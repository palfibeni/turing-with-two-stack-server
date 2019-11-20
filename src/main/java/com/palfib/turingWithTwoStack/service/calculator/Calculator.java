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
            return null;
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
            this.acceptedCalculationConditions.add(this.condition);

            // Checking currentState
            val currentState = this.condition.getCurrentState();
            setAcceptedRun(currentState);
            if (this.accepted != null) return;

            // take a step
            this.numberOfSteps += 1;
            val validRules = machine.getRules().stream()
                    .filter(rule -> this.condition.isValidRule(rule))
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

        private void setAcceptedRun(final MachineState currentState) {
            // Current state is Accept state -> accept = true
            if (machine.getAcceptStates().contains(currentState)) {
                this.accepted = true;
            }
            // Current state is Decline state -> accept = false
            if (machine.getDeclineStates().contains(currentState)) {
                this.accepted = false;
            }
            // We are over 1000 steps -> accept = false
            if (this.numberOfSteps > 10000) {
                this.accepted = false;
            }
        }

        /**
         * Opens a new Calculation for each valid rule
         *
         * @param validRules: the list of valid rules
         */
        private void handleNonDeterministicRules(final List<? extends Rule> validRules) {
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
            }
        }

        protected abstract C copyCondition(final MachineState nextState);
    }
}
