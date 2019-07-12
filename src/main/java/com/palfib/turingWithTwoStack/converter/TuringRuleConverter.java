package com.palfib.turingWithTwoStack.converter;

import com.palfib.turingWithTwoStack.dto.TuringRuleDto;
import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.enums.Direction;
import com.palfib.turingWithTwoStack.entity.TuringRule;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class TuringRuleConverter {

    public static List<TuringRule> fromDtos(final List<TuringRuleDto> dtos, final List<MachineState> states) {
        return dtos.stream().map(dto -> TuringRuleConverter.fromDto(dto, states)).collect(toList());
    }

    public static TuringRule fromDto(final TuringRuleDto dto, final List<MachineState> states) {
        return TuringRule.builder()
                .fromState(getStateFromDto(dto.getFromState(), states))
                .toState(getStateFromDto(dto.getToState(), states))
                .readCharacter(dto.getReadCharacter())
                .writeCharacter(dto.getWriteCharacter())
                .direction(Direction.valueOf(dto.getDirection()))
                .build();
    }

    private static MachineState getStateFromDto(final String dtoState, final List<MachineState> states) {
        return states.stream()
                .filter(state -> state.getName().equals(dtoState))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The from or to State cannot be empty"));
    }

    public static List<TuringRuleDto> toDtos(final List<TuringRule> entities) {
        return entities.stream().map(TuringRuleConverter::toDto).collect(toList());
    }

    public static TuringRuleDto toDto(final TuringRule entity) {
        return TuringRuleDto.builder()
                .fromState(entity.getFromState().getName())
                .toState(entity.getToState().getName())
                .readCharacter(entity.getReadCharacter())
                .writeCharacter(entity.getWriteCharacter())
                .direction(entity.getDirection().name())
                .build();
    }
}
