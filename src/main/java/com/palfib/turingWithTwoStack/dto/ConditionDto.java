package com.palfib.turingWithTwoStack.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonDeserialize(builder = ConditionDto.ConditionDtoBuilder.class)
public class ConditionDto {

    private String currentState;

    private Character currentPostion;

    private List<Character> charactersAhead;

    private List<Character> charactersBehind;


    @JsonPOJOBuilder(withPrefix = "")
    public static class ConditionDtoBuilder{

    }
}
