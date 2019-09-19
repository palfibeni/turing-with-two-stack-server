package com.palfib.turingWithTwoStack.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonDeserialize(builder = TuringRuleDto.TuringRuleDtoBuilder.class)
public class TuringRuleDto {

    private Long id;

    private Long fromState;

    private Character readCharacter;

    private Long toState;

    private Character writeCharacter;

    private String direction;

    @JsonPOJOBuilder(withPrefix = "")
    public static class TuringRuleDtoBuilder{

    }
}
