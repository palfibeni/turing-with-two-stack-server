package com.palfib.turingWithTwoStack.service.calculator;

import com.palfib.turingWithTwoStack.entity.Condition;
import com.palfib.turingWithTwoStack.entity.Machine;
import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.Rule;
import lombok.Getter;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Calculator<T extends Machine, C extends Condition>{

    private final T machine;

    Calculator(final T machine) {
        this.machine = machine;
    }

    public List<C> calculate(final String input) {
        //TODO introduce Trampoline:
        // https://github.com/bodar/totallylazy
        // https://medium.com/@johnmcclean/trampolining-a-practical-guide-for-awesome-java-developers-4b657d9c3076
        // https://medium.com/@johnmcclean/java-fun-recursive-concurrency-the-easy-way-f2c7cc02db28
        // https://www.slideshare.net/mariofusco/lazine


        // Currently implemented like this:
        // https://stackoverflow.com/questions/706048/using-threads-and-recursion-in-java-to-calculate-fibonacci-numbers
        val calculation = createCalculation(initState(this.machine.getStartState(), input), 0);
        try {
            calculation.start();
            calculation.join();
            if (calculation.accepted) {
                return calculation.getAcceptedCalculationConditions();
            }
        } catch (InterruptedException e) {
            // TODO exception handling
        }
        return null;
    }

    protected abstract C initState(final MachineState startState, final String input);

    protected abstract Calculation createCalculation(final C nextCondition, int numberOfSteps);

    protected abstract class Calculation extends Thread {

        C condition;

        @Getter
        private final List<C> acceptedCalculationConditions = new ArrayList<>();

        private Boolean accepted = null;

        private int numberOfSteps;


        Calculation(final C condition, final int numberOfSteps) {
            this.condition = condition;
            this.numberOfSteps = numberOfSteps;
        }

        @Override
        public void run() {
            step();
        }

        private void step() {
            // Adding the last state to the acceptedCalculationConditions
            this.acceptedCalculationConditions.add(condition);

            // Checking currentState
            val currentState = this.condition.getCurrentState();
            if (setAccepted(currentState)) return;

            // take a step
            this.numberOfSteps += 1;
            val currentPosition = this.condition.getCurrentPosition();
            val validRules = ((Set<Rule>) machine.getRules()).stream()
                    .filter(rule -> isValidRule(rule, currentPosition, currentState))
                    .collect(Collectors.toList());
            if (validRules.isEmpty()) {
                this.accepted = false;
            }
            if (validRules.size() == 1) {
                final Rule rule = validRules.get(0);
                this.condition = copyCondition(rule.getToState());
                this.condition.useRule(rule);
                step();
            }
            if (validRules.size() > 1) {
                handleNonDeterministicRules(validRules);
            }
        }

        private boolean setAccepted(final MachineState currentState) {
            if (machine.getAcceptStates().contains(currentState)) {
                this.accepted = true;
                return true;
            }
            if (machine.getDeclineStates().contains(currentState)) {
                this.accepted = false;
                return true;
            }
            if (this.numberOfSteps > 1000) {
                this.accepted = false;
                return true;
            }
            return false;
        }

        /**
         * Opens a new Calculation for each valid rule
         *
         * @param validRules: the list of valid rules
         */
        private void handleNonDeterministicRules(final List<Rule> validRules) {
            try {
                for (val rule : validRules) {
                    val nextCondition = copyCondition(rule.getToState());
                    nextCondition.useRule(rule);
                    val calculation = createCalculation(nextCondition, this.numberOfSteps);
                    calculation.start();
                    calculation.join();
                    if (calculation.accepted) {
                        this.accepted = true;
                        this.acceptedCalculationConditions.addAll(calculation.acceptedCalculationConditions);
                    }
                }
            } catch (final InterruptedException e) {
                // TODO exception handling
            }
        }

        private boolean isValidRule(final Rule rule, final Character currentPosition, final MachineState currentState) {
            return rule.getFromState().equals(currentState)
                    && rule.getReadCharacter().equals(currentPosition);
        }

        protected abstract C copyCondition(final MachineState nextState);
    }
}
