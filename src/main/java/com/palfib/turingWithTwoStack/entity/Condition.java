package com.palfib.turingWithTwoStack.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public abstract class Condition {

    public static final Character EMPTY = '_';
    public static final String EMPTY_AS_STRING = "_";

    private final MachineState currentState;

    public abstract Character getCurrentPosition();

    public abstract boolean isValidRule(final Rule rule);

    public abstract void useRule(final Rule rule);

    public abstract List<Character> getCharactersAhead();

    public abstract List<Character> getCharactersBehind();
}
