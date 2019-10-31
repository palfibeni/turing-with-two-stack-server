package com.palfib.turingWithTwoStack.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.palfib.turingWithTwoStack.entity.MachineState;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonDeserialize(builder = ConditionDto.ConditionDtoBuilder.class)
public class ConditionDto {

    private MachineStateDto currentState;

    private Character currentPosition;

    private List<Character> charactersAhead;

    private List<Character> charactersBehind;


    @JsonPOJOBuilder(withPrefix = "")
    public static class ConditionDtoBuilder{

    }
}
