package com.palfib.turingWithTwoStack.dto.turingMachine;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder(toBuilder = true)
@JsonDeserialize(builder = TuringMachineDto.TuringMachineDtoBuilder.class)
public class TuringMachineDto {

    private Set<Character> inputCharacters;

    private Set<Character> tapeCharacters;

    private Set<String> states;

    private String startState;

    private Set<String> acceptStates;

    private Set<String> declineStates;

    @JsonPOJOBuilder(withPrefix = "")
    public static class TuringMachineDtoBuilder {
    }

}
