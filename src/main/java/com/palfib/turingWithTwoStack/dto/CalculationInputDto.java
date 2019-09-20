package com.palfib.turingWithTwoStack.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonDeserialize(builder = CalculationInputDto.CalculationInputDtoBuilder.class)
public class CalculationInputDto {

    private TuringMachineDto turingMachine;

    private String input;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CalculationInputDtoBuilder{

    }
}
