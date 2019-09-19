package com.palfib.turingWithTwoStack.service.converter;

import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import com.palfib.turingWithTwoStack.entity.twoStack.TwoStackMachine;

public class TwoStackMachineConverter {

    public static TwoStackMachine fromTuringMachine(final TuringMachine turingMachine) {
        return TwoStackMachine.builder()
                .inputCharacters(turingMachine.getInputCharacters())
                .states(turingMachine.getStates())
                .rules(TwoStackRuleConverter.fromTuringRules(turingMachine.getRules()))
                .build();
    }

}
