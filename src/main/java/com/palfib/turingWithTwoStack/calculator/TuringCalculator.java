package com.palfib.turingWithTwoStack.calculator;

import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.turingMachine.TuringMachine;
import com.palfib.turingWithTwoStack.entity.turingMachine.TuringRule;
import com.palfib.turingWithTwoStack.entity.turingMachine.TuringState;
import lombok.Getter;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TuringCalculator {

    private final TuringMachine turingMachine;

    public TuringCalculator(final TuringMachine turingMachine) {
        this.turingMachine = turingMachine;
    }

    public List<TuringState> calculate(final String input) {
        //TODO introduce Trampoline:
        // https://github.com/bodar/totallylazy
        // https://medium.com/@johnmcclean/trampolining-a-practical-guide-for-awesome-java-developers-4b657d9c3076
        // https://medium.com/@johnmcclean/java-fun-recursive-concurrency-the-easy-way-f2c7cc02db28
        // https://www.slideshare.net/mariofusco/lazine


        // Currently implemented like this:
        // https://stackoverflow.com/questions/706048/using-threads-and-recursion-in-java-to-calculate-fibonacci-numbers
        val calculation = new TuringCalculation(new TuringState(this.turingMachine.getStartState(), input), 0);
        try {
            calculation.start();
            calculation.join();
            if (calculation.accepted) {
                return calculation.getAcceptedCalculationStates();
            }
        } catch (InterruptedException e) {
            // Not handled
        }
        return null;
    }

    public class TuringCalculation extends Thread {

        private TuringState turingState;

        @Getter
        private final List<TuringState> acceptedCalculationStates = new ArrayList<>();

        private Boolean accepted = null;

        private int numberOfSteps;


        public TuringCalculation(final TuringState turingState, final int numberOfSteps) {
            this.turingState = new TuringState(turingState);
            this.numberOfSteps = numberOfSteps;
        }

        @Override
        public void run() {
            takeAStep();
        }

        public void takeAStep() {
            // Adding the last state to the acceptedCalculationStates
            acceptedCalculationStates.add(turingState);

            // Checking currentState
            val currentState = getCurrentState();
            if (setAccepted(currentState)) return;

            // take a step
            numberOfSteps += 1;
            val currentPosition = getCurrentPosition();
            val validRules = turingMachine.getRules().stream()
                    .filter(rule -> isValidRule(rule, currentPosition, currentState))
                    .collect(Collectors.toList());
            if (validRules.isEmpty()) {
                this.accepted = false;
                return;
            }
            if (validRules.size() == 1) {
                final TuringRule rule = validRules.get(0);
                this.turingState = new TuringState(rule.getToState(), turingState.getTape());
                this.turingState.getTape().useRule(rule.getDirection(), rule.getWriteCharacter());
                takeAStep();
                return;
            }
            try {
                for (val rule : validRules) {
                    val nextTuringState = new TuringState(rule.getToState(), turingState.getTape());
                    nextTuringState.getTape().useRule(rule.getDirection(), rule.getWriteCharacter());
                    val calculation = new TuringCalculation(nextTuringState, numberOfSteps);
                    calculation.start();
                    calculation.join();
                    if(calculation.accepted) {
                        this.accepted = true;
                        this.acceptedCalculationStates.addAll(calculation.acceptedCalculationStates);
                    }
                }
            } catch (InterruptedException e) {
                // Not handled
            }
        }

        private boolean setAccepted(MachineState currentState) {
            if (turingMachine.getAcceptStates().contains(currentState)) {
                this.accepted = true;
                return true;
            }
            if (turingMachine.getDeclineStates().contains(currentState)) {
                this.accepted = false;
                return true;
            }
            if (this.numberOfSteps > 100) {
                this.accepted = false;
                return true;
            }
            return false;
        }

        private boolean isValidRule(TuringRule rule, Character currentPosition, MachineState currentState) {
            return rule.getFromState().equals(currentState)
                    && rule.getReadCharacter().equals(currentPosition);
        }

        private MachineState getCurrentState() {
            return turingState.getCurrentState();
        }

        private Character getCurrentPosition() {
            return turingState.getTape().getCurrentPosition();
        }
    }
}
