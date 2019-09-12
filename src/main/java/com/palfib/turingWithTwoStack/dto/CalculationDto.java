package com.palfib.turingWithTwoStack.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;

import java.util.List;

@Builder
@JsonDeserialize(builder = CalculationDto.CalculationDtoBuilder.class)
public class CalculationDto {

    private List<ConditionDto> turingConditions;

    private List<ConditionDto> twoStackCalculations;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CalculationDtoBuilder{

    }
}
