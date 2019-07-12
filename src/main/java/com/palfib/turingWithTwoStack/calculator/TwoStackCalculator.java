package com.palfib.turingWithTwoStack.calculator;

import com.palfib.turingWithTwoStack.entity.*;

public class TwoStackCalculator extends AbstractCalculator<TwoStackMachine, TwoStackCondition> {

    public TwoStackCalculator(final TwoStackMachine machine) {
        super(machine);
    }

    public TwoStackCondition initState(final MachineState startState, final String input) {
        return new TwoStackCondition(startState, input);
    }

    @Override
    public AbstractCalculation createCalculation(final TwoStackCondition nextCondition, final int numberOfSteps) {
        return new TwoStackCalculation(nextCondition, numberOfSteps);
    }

    public class TwoStackCalculation extends AbstractCalculation {

        private TwoStackCalculation(final TwoStackCondition condition, final int numberOfSteps) {
            super(condition, numberOfSteps);
        }

        @Override
        protected TwoStackCondition copyCondition(final MachineState nextState) {
            return new TwoStackCondition(nextState, condition.getLeftStack(), condition.getRightStack());
        }
    }
}
