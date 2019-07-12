package com.palfib.turingWithTwoStack.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.val;

import java.util.Objects;

@Getter
public class TwoStackRule extends Rule {

    private String writeLeft;

    private String writeRight;

    private boolean copyLeftToWrite;

    @Builder
    public TwoStackRule(final MachineState fromState, final Character readCharacter, final MachineState toState,
                        final String writeLeft, final String writeRight, final boolean copyLeftToWrite) {
        super(fromState, readCharacter, toState);
        this.writeLeft = writeLeft;
        this.writeRight = writeRight;
        this.copyLeftToWrite = copyLeftToWrite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        val that = (TwoStackRule) o;
        return copyLeftToWrite == that.copyLeftToWrite
                && writeLeft.equals(that.writeLeft)
                && writeRight.equals(that.writeRight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), writeLeft, writeRight, copyLeftToWrite);
    }
}
