package com.palfib.turingWithTwoStack.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@EqualsAndHashCode(exclude = {"created"})
@JsonDeserialize(builder = TuringRuleDto.TuringRuleDtoBuilder.class)
public class TuringRuleDto {

    private Long id;

    private MachineStateDto fromState;

    private Character readCharacter;

    private MachineStateDto toState;

    private Character writeCharacter;

    private String direction;

    private Date created;

    @JsonPOJOBuilder(withPrefix = "")
    public static class TuringRuleDtoBuilder{

    }
}
