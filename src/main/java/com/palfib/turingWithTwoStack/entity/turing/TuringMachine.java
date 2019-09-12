package com.palfib.turingWithTwoStack.entity.turing;

import com.palfib.turingWithTwoStack.entity.Machine;
import com.palfib.turingWithTwoStack.entity.MachineState;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
public class TuringMachine extends Machine<TuringRule> {

    @Builder
    public TuringMachine(final Set<Character> inputCharacters, final Set<MachineState> states,
                         final MachineState startState, final Set<MachineState> acceptStates,
                         final Set<MachineState> declineStates, final Set<TuringRule> rules) {
        super(inputCharacters, states, startState, acceptStates, declineStates, rules);
    }
}
