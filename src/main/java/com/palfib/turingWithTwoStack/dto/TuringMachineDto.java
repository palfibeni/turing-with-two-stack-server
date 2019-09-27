package com.palfib.turingWithTwoStack.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder(toBuilder = true)
@JsonDeserialize(builder = TuringMachineDto.TuringMachineDtoBuilder.class)
public class TuringMachineDto {

    private Long id;

    private String name;

    private Set<Character> tapeCharacters;

    private Set<MachineStateDto> states;

    private Set<TuringRuleDto> rules;

    @JsonPOJOBuilder(withPrefix = "")
    public static class TuringMachineDtoBuilder {
    }

}
