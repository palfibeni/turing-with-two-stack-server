package com.palfib.turingWithTwoStack.service.calculator;

import com.palfib.turingWithTwoStack.entity.*;
import com.palfib.turingWithTwoStack.entity.twoStack.TwoStackCondition;
import com.palfib.turingWithTwoStack.entity.twoStack.TwoStackMachine;

public class TwoStackCalculator extends Calculator<TwoStackMachine, TwoStackCondition> {

    public TwoStackCalculator(final TwoStackMachine machine) {
        super(machine);
    }

    @Override
    protected TwoStackCondition initState(final MachineState startState, final String input) {
        return new TwoStackCondition(startState, input);
    }

    @Override
    protected Calculation createCalculation(final TwoStackCondition nextCondition, final int numberOfSteps) {
        return new TwoStackCalculation(nextCondition, numberOfSteps);
    }

    private class TwoStackCalculation extends Calculation {

        private TwoStackCalculation(final TwoStackCondition condition, final int numberOfSteps) {
            super(condition, numberOfSteps);
        }

        @Override
        protected TwoStackCondition copyCondition(final MachineState nextState) {
            return new TwoStackCondition(nextState, condition.getLeftStack(), condition.getRightStack());
        }
    }
}
