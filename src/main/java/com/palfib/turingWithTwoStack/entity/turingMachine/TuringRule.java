package com.palfib.turingWithTwoStack.entity.turingMachine;

import com.palfib.turingWithTwoStack.entity.Rule;
import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.enums.Direction;
import lombok.*;

@Getter
public class TuringRule extends Rule {

    private Direction direction;

    @Builder
    public TuringRule(final MachineState fromState, final MachineState toState, final Character readCharacter,
                      final Character writeCharacter, final Direction direction) {
        super(fromState, toState, readCharacter, writeCharacter);
        this.direction = direction;
    }

    public TuringRule(TuringRule turingRule) {
        super(turingRule);
        this.direction = turingRule.direction;
    }
}
