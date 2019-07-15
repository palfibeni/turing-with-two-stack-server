package com.palfib.turingWithTwoStack.converter;

import com.palfib.turingWithTwoStack.entity.TuringMachine;
import com.palfib.turingWithTwoStack.entity.TwoStackMachine;

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
