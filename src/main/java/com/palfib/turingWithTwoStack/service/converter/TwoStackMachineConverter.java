package com.palfib.turingWithTwoStack.service.converter;

import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import com.palfib.turingWithTwoStack.entity.twoStack.TwoStackMachine;

public class TwoStackMachineConverter {

    public static TwoStackMachine fromTuringMachine(final TuringMachine turingMachine) {
        return TwoStackMachine.builder()
                .inputCharacters(turingMachine.getInputCharacters())
                .states(turingMachine.getStates())
                .startState(turingMachine.getStartState())
                .acceptStates(turingMachine.getAcceptStates())
                .declineStates(turingMachine.getDeclineStates())
                .rules(TwoStackRuleConverter.fromTuringRules(turingMachine.getRules()))
                .build();
    }

}
