package com.palfib.turingWithTwoStack.service.converter;

import com.palfib.turingWithTwoStack.dto.TuringRuleDto;
import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.enums.Direction;
import com.palfib.turingWithTwoStack.entity.turing.TuringRule;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class TuringRuleConverter {

    public static Set<TuringRule> fromDtos(final Set<TuringRuleDto> dtos, final Set<MachineState> states) {
        return dtos.stream().map(dto -> TuringRuleConverter.fromDto(dto, states)).collect(toSet());
    }

    public static TuringRule fromDto(final TuringRuleDto dto, final Set<MachineState> states) {
        return TuringRule.builder()
                .fromState(getStateFromDto(dto.getFromState(), states))
                .toState(getStateFromDto(dto.getToState(), states))
                .readCharacter(dto.getReadCharacter())
                .writeCharacter(dto.getWriteCharacter())
                .direction(Direction.valueOf(dto.getDirection()))
                .build();
    }

    private static MachineState getStateFromDto(final String dtoState, final Set<MachineState> states) {
        return states.stream()
                .filter(state -> state.getName().equals(dtoState))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The from or to State cannot be empty"));
    }

    public static Set<TuringRuleDto> toDtos(final Set<TuringRule> entities) {
        return entities.stream().map(TuringRuleConverter::toDto).collect(toSet());
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
