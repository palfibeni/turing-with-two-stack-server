package com.palfib.turingWithTwoStack.entity.turingMachine;

import com.palfib.turingWithTwoStack.entity.MachineState;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TuringState {

    private MachineState currentState;

    private final Tape tape;

    public TuringState(final MachineState currentState, final String input) {
        this.currentState = currentState;
        this.tape = new Tape(input);
    }

    public TuringState(final TuringState turingState) {
        this(turingState.currentState, turingState.tape);
    }

    public TuringState(MachineState currentState, Tape tape) {
        this.currentState = currentState;
        this.tape = new Tape(tape);
    }
}
