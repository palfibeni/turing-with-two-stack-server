package com.palfib.turingWithTwoStack.entity.twoStack;

import com.palfib.turingWithTwoStack.entity.Machine;
import com.palfib.turingWithTwoStack.entity.MachineState;
import lombok.*;

import java.util.Set;

@Data
public class TwoStackMachine extends Machine<TwoStackRule> {

    @Builder
    public TwoStackMachine(
            final Set<Character> inputCharacters, final Set<MachineState> states, final Set<TwoStackRule> rules) {
        super(inputCharacters, states, rules);
    }
}
