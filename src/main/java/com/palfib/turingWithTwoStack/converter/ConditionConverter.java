package com.palfib.turingWithTwoStack.converter;

import com.palfib.turingWithTwoStack.dto.ConditionDto;
import com.palfib.turingWithTwoStack.entity.Condition;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ConditionConverter {

    private final MachineStateConverter machineStateConverter;

    public ConditionConverter(final MachineStateConverter machineStateConverter) {
        this.machineStateConverter = machineStateConverter;
    }

    public List<ConditionDto> toDtos(final List<? extends Condition> conditions) {
        return conditions.stream().map(this::toDto).collect(toList());
    }

    public ConditionDto toDto(final Condition condition) {
        return ConditionDto.builder()
                .currentState(machineStateConverter.toDto(condition.getCurrentState()))
                .currentPosition(condition.getCurrentPosition())
                .charactersAhead(condition.getCharactersAhead())
                .charactersBehind(condition.getCharactersBehind())
                .build();
    }
}
