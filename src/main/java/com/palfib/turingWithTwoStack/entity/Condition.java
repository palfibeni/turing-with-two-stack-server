package com.palfib.turingWithTwoStack.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Condition {

    public static final Character EMPTY = '_';
    public static final String EMPTY_AS_STRING = "_";

    private MachineState currentState;

    public abstract Character getCurrentPosition();

    public abstract void useRule(final Rule rule);
}
