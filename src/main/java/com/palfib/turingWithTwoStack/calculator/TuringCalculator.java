package com.palfib.turingWithTwoStack.calculator;

import com.palfib.turingWithTwoStack.entity.*;

public class TuringCalculator extends AbstractCalculator<TuringMachine, TuringCondition> {

    public TuringCalculator(final TuringMachine machine) {
        super(machine);
    }

    public TuringCondition initState(final MachineState startState, final String input) {
        return new TuringCondition(startState, input);
    }

    @Override
    public AbstractCalculation createCalculation(final TuringCondition nextCondition, final int numberOfSteps) {
        return new TuringCalculation(nextCondition, numberOfSteps);
    }

    public class TuringCalculation extends AbstractCalculation {

        private TuringCalculation(final TuringCondition condition, final int numberOfSteps) {
            super(condition, numberOfSteps);
        }

        @Override
        protected TuringCondition copyCondition(final MachineState nextState) {
            return new TuringCondition(nextState, condition.getTuringTape());
        }
    }
}
