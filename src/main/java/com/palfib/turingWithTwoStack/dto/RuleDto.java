package com.palfib.turingWithTwoStack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class RuleDto {

    private String fromState;

    private String toState;

    private Character readCharacter;

    private Character writeCharacter;
}
