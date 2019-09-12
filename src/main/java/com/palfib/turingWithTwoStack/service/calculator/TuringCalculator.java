package com.palfib.turingWithTwoStack.service.calculator;

import com.palfib.turingWithTwoStack.entity.*;
import com.palfib.turingWithTwoStack.entity.turing.TuringCondition;
import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;

public class TuringCalculator extends Calculator<TuringMachine, TuringCondition> {

    public TuringCalculator(final TuringMachine machine) {
        super(machine);
    }

    @Override
    protected TuringCondition initState(final MachineState startState, final String input) {
        return new TuringCondition(startState, input);
    }

    @Override
    protected Calculation createCalculation(final TuringCondition nextCondition, final int numberOfSteps) {
        return new TuringCalculation(nextCondition, numberOfSteps);
    }

    private class TuringCalculation extends Calculation {

        private TuringCalculation(final TuringCondition condition, final int numberOfSteps) {
            super(condition, numberOfSteps);
        }

        @Override
        protected TuringCondition copyCondition(final MachineState nextState) {
            return new TuringCondition(nextState, condition.getTuringTape());
        }
    }
}
