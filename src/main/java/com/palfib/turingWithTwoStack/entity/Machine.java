package com.palfib.turingWithTwoStack.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
public abstract class Machine<T extends Rule> {

    private Set<Character> inputCharacters = new HashSet<>();

    private Set<MachineState> states = new HashSet<>();

    private MachineState startState;

    private Set<MachineState> acceptStates = new HashSet<>();

    private Set<MachineState> declineStates = new HashSet<>();

    private Set<T> rules = new HashSet<>();
}
