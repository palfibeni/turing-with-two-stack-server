package com.palfib.turingWithTwoStack.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonDeserialize(builder = CalculationDto.CalculationDtoBuilder.class)
public class CalculationDto {

    private List<ConditionDto> turingConditions;

    private List<ConditionDto> twoStackConditions;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CalculationDtoBuilder{

    }
}
