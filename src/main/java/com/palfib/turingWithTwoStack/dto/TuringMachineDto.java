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

    private Set<Character> tapeCharacters;

    private Set<String> states;

    private String startState;

    private Set<String> acceptStates;

    private Set<String> declineStates;

    private Set<TuringRuleDto> rules;

    @JsonPOJOBuilder(withPrefix = "")
    public static class TuringMachineDtoBuilder {
    }

}
