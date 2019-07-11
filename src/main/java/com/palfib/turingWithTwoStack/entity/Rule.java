package com.palfib.turingWithTwoStack.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Rule {

    private MachineState fromState;

    private MachineState toState;

    private Character readCharacter;

    private Character writeCharacter;

    public Rule(Rule rule) {
        this.fromState = rule.fromState;
        this.toState = rule.fromState;
        this.readCharacter = rule.readCharacter;
        this.writeCharacter = rule.writeCharacter;
    }

}
