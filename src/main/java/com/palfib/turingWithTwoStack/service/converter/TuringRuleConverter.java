package com.palfib.turingWithTwoStack.service.converter;

import com.palfib.turingWithTwoStack.dto.TuringRuleDto;
import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.enums.Direction;
import com.palfib.turingWithTwoStack.entity.turing.TuringRule;

import java.util.Set;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

public class TuringRuleConverter {

    private static Long ruleId = 1L;

    public static Set<TuringRule> fromDtos(final Set<TuringRuleDto> dtos, final Set<MachineState> states) {
        ruleId = 1L;
        return dtos.stream().map(dto -> TuringRuleConverter.fromDto(dto, states)).collect(toSet());
    }

    private static TuringRule fromDto(final TuringRuleDto dto, final Set<MachineState> states) {
        return TuringRule.builder()
                .id(ofNullable(dto.getId()).orElse(ruleId++))
                .fromState(getStateFromDto(dto.getFromState(), states))
                .toState(getStateFromDto(dto.getToState(), states))
                .readCharacter(dto.getReadCharacter())
                .writeCharacter(dto.getWriteCharacter())
                .direction(Direction.valueOf(dto.getDirection()))
                .build();
    }

    private static MachineState getStateFromDto(final Long stateId, final Set<MachineState> states) {
        return states.stream()
                .filter(state -> state.getId().equals(stateId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The from or to State cannot be empty"));
    }

    public static Set<TuringRuleDto> toDtos(final Set<TuringRule> entities) {
        return entities.stream().map(TuringRuleConverter::toDto).collect(toSet());
    }

    private static TuringRuleDto toDto(final TuringRule entity) {
        return TuringRuleDto.builder()
                .id(entity.getId())
                .fromState(entity.getFromState().getId())
                .toState(entity.getToState().getId())
                .readCharacter(entity.getReadCharacter())
                .writeCharacter(entity.getWriteCharacter())
                .direction(entity.getDirection().name())
                .build();
    }
}
