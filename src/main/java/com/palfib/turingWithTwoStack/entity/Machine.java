package com.palfib.turingWithTwoStack.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Getter
@AllArgsConstructor
public abstract class Machine<T extends Rule> {

    private Set<Character> inputCharacters = new HashSet<>();

    private Set<MachineState> states = new HashSet<>();

    private Set<T> rules = new HashSet<>();

    public MachineState getStartState() {
        // TODO validálni kéne
        return states.stream().filter(MachineState::isStart).findFirst().orElse(null);
    }

    public Set<MachineState> getAcceptStates() {
        return states.stream().filter(MachineState::isAccept).collect(toSet());
    }

    public Set<MachineState> getDeclineStates() {
        return states.stream().filter(MachineState::isDecline).collect(toSet());
    }
}
