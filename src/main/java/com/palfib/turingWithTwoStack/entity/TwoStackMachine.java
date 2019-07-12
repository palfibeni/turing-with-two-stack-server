package com.palfib.turingWithTwoStack.entity;

import lombok.*;

import java.util.Set;

@Data
public class TwoStackMachine extends Machine<TwoStackRule>{

    @Builder
    public TwoStackMachine(final Set<Character> inputCharacters, final Set<MachineState> states,
                           final MachineState startState, final Set<MachineState> acceptStates,
                           final Set<MachineState> declineStates, final Set<TwoStackRule> rules) {
        super(inputCharacters, states, startState, acceptStates, declineStates, rules);
    }
}
