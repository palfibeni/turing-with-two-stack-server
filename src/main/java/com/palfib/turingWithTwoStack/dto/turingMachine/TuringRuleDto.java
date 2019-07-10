package com.palfib.turingWithTwoStack.dto.turingMachine;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.palfib.turingWithTwoStack.dto.RuleDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonDeserialize(builder = TuringRuleDto.TuringRuleDtoBuilder.class)
public class TuringRuleDto extends RuleDto {

    private String direction;

    @Builder
    public TuringRuleDto(final String fromState, final String toState, final Character readCharacter,
                      final Character writeCharacter, final String direction) {
        super(fromState, toState, readCharacter, writeCharacter);
        this.direction = direction;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class TuringRuleDtoBuilder{

    }
}
