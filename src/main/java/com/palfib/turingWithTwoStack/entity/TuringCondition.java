package com.palfib.turingWithTwoStack.entity;

import com.palfib.turingWithTwoStack.entity.enums.Direction;
import lombok.Getter;
import lombok.val;

@Getter
public class TuringCondition extends Condition {

    private final TuringTape turingTape;

    public TuringCondition(final MachineState currentState, final String input) {
        super(currentState);
        this.turingTape = new TuringTape(input);
    }

    public TuringCondition(final TuringCondition turingCondition) {
        this(turingCondition.getCurrentState(), turingCondition.turingTape);
    }

    public TuringCondition(final MachineState currentState, final TuringTape turingTape) {
        super(currentState);
        this.turingTape = new TuringTape(turingTape);
    }

    @Override
    public Character getCurrentPosition() {
        return turingTape.getCurrentPosition();
    }

    @Override
    public void useRule(final Rule rule) {
        if(!(rule instanceof TuringRule)) {
            throw new IllegalStateException("Only TuringRule can be used on TuringState!");
        }
        val turingRule = (TuringRule) rule;
        val direction = turingRule.getDirection();
        val writeCharacter = turingRule.getWriteCharacter();
        switch (direction){
            case FORWARD:
                this.turingTape.moveCursorForward(writeCharacter);
                break;
            case BACKWARD:
                this.turingTape.moveCursorBackward(writeCharacter);
                break;
            case STAY:
                this.turingTape.stayWithCursor(writeCharacter);
                break;
            default:
                throw new IllegalStateException("Not known direction: " + direction);
        }
    }
}
