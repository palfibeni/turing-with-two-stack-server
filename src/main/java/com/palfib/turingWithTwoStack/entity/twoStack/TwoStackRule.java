package com.palfib.turingWithTwoStack.entity.twoStack;

import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.Rule;
import lombok.Builder;
import lombok.Getter;
import lombok.val;

import java.util.Date;
import java.util.Objects;

@Getter
public class TwoStackRule extends Rule {

    private final Character readRight;

    private final Character readLeft;

    private final String writeLeft;

    private final String writeRight;

    private final boolean copyLeftToRight;

    @Builder
    public TwoStackRule(final Long id, final MachineState fromState, final Character readCharacter,
                        final MachineState toState, final Character readRight, final Character readLeft,
                        final String writeLeft, final String writeRight,
                        final boolean copyLeftToRight, final Date created) {
        super(id, fromState, readCharacter, toState, created);
        this.readRight = readRight;
        this.readLeft = readLeft;
        this.writeLeft = writeLeft;
        this.writeRight = writeRight;
        this.copyLeftToRight = copyLeftToRight;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        val that = (TwoStackRule) o;
        return copyLeftToRight == that.copyLeftToRight
                && Objects.equals(readRight, that.readRight)
                && Objects.equals(readLeft, that.readLeft)
                && Objects.equals(writeLeft, that.writeLeft)
                && Objects.equals(writeRight, that.writeRight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), readLeft, readRight, writeLeft, writeRight, copyLeftToRight);
    }
}
