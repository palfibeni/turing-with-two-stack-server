package com.palfib.turingWithTwoStack.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public abstract class Rule {

    private MachineState fromState;

    private Character readCharacter;

    private MachineState toState;
}
