package com.palfib.turingWithTwoStack.service.converter;

import com.palfib.turingWithTwoStack.dto.ConditionDto;
import com.palfib.turingWithTwoStack.entity.Condition;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ConditionConverter {

    public static List<ConditionDto> toDtos(final List<? extends Condition> conditions) {
        return conditions.stream().map(ConditionConverter::toDto).collect(toList());
    }

    public static ConditionDto toDto(final Condition condition) {
        return ConditionDto.builder()
                .currentState(condition.getCurrentState().getName())
                .currentPostion(condition.getCurrentPosition())
                .charactersAhead(condition.getCharactersAhead())
                .charactersBehind(condition.getCharactersBehind())
                .build();
    }
}
