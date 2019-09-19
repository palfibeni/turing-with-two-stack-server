package com.palfib.turingWithTwoStack.entity.turing;

import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.Rule;
import com.palfib.turingWithTwoStack.entity.enums.Direction;
import lombok.Builder;
import lombok.Getter;
import lombok.val;

import java.util.Objects;

@Getter
public class TuringRule extends Rule {

    private Character writeCharacter;

    private Direction direction;

    @Builder
    public TuringRule(final Long id, final MachineState fromState, final Character readCharacter, final MachineState toState,
                      final Character writeCharacter, final Direction direction) {
        super(id, fromState, readCharacter, toState);
        this.writeCharacter = writeCharacter;
        this.direction = direction;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        val that = (TuringRule) o;
        return writeCharacter.equals(that.writeCharacter) && direction == that.direction;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(writeCharacter, direction);
    }
}
