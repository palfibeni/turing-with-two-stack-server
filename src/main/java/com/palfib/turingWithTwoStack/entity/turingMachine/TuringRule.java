package com.palfib.turingWithTwoStack.entity.turingMachine;

import com.palfib.turingWithTwoStack.entity.Rule;
import com.palfib.turingWithTwoStack.entity.State;
import com.palfib.turingWithTwoStack.entity.enums.Direction;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
public class TuringRule extends Rule {

    private Direction direction;

    @Builder
    public TuringRule(final State fromState, final State toState, final Character readCharacter,
                      final Character writeCharacter, final Direction direction) {
        super(fromState, toState, readCharacter, writeCharacter);
        this.direction = direction;
    }
}
