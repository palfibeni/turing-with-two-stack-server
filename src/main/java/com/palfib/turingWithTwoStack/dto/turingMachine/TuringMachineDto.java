package com.palfib.turingWithTwoStack.dto.turingMachine;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.palfib.turingWithTwoStack.dto.RuleDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
@JsonDeserialize(builder = TuringMachineDto.TuringMachineDtoBuilder.class)
public class TuringMachineDto {

    private List<Character> inputCharacters;

    private List<Character> tapeCharacters;

    private List<String> states;

    private String startState;

    private List<String> acceptStates;

    private List<TuringRuleDto> rules;

    @JsonPOJOBuilder(withPrefix = "")
    public static class TuringMachineDtoBuilder {
    }

}
