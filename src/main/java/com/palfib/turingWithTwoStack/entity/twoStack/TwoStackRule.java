package com.palfib.turingWithTwoStack.entity.twoStack;

import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.Rule;
import lombok.Builder;
import lombok.Getter;
import lombok.val;

import java.util.Objects;

@Getter
public class TwoStackRule extends Rule {

    private String writeLeft;

    private String writeRight;

    private boolean copyLeftToRight;

    @Builder
    public TwoStackRule(final MachineState fromState, final Character readCharacter, final MachineState toState,
                        final String writeLeft, final String writeRight, final boolean copyLeftToRight) {
        super(fromState, readCharacter, toState);
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
                && writeLeft.equals(that.writeLeft)
                && writeRight.equals(that.writeRight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), writeLeft, writeRight, copyLeftToRight);
    }
}
