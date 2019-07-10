package com.palfib.turingWithTwoStack.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public abstract class Rule {

    private State fromState;

    private State toState;

    private Character readCharacter;

    private Character writeCharacter;

}
