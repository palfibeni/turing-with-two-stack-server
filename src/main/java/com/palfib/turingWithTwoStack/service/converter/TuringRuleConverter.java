package com.palfib.turingWithTwoStack.service.converter;

import com.palfib.turingWithTwoStack.dto.TuringRuleDto;
import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.enums.Direction;
import com.palfib.turingWithTwoStack.entity.turing.TuringRule;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

@Component
public class TuringRuleConverter {

    private Long ruleId = 1L;

    public Set<TuringRule> fromDtos(final Set<TuringRuleDto> dtos, final Set<MachineState> states) {
        ruleId = 1L;
        return dtos.stream().map(dto -> this.fromDto(dto, states)).collect(toSet());
    }

    private TuringRule fromDto(final TuringRuleDto dto, final Set<MachineState> states) {
        return TuringRule.builder()
                .id(ofNullable(dto.getId()).orElse(ruleId++))
                .fromState(getStateFromDto(dto.getFromState(), states))
                .toState(getStateFromDto(dto.getToState(), states))
                .readCharacter(dto.getReadCharacter())
                .writeCharacter(dto.getWriteCharacter())
                .direction(Direction.valueOf(dto.getDirection()))
                .build();
    }

    private MachineState getStateFromDto(final Long stateId, final Set<MachineState> states) {
        return states.stream()
                .filter(state -> state.getId().equals(stateId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The from or to State cannot be empty"));
    }

    public Set<TuringRuleDto> toDtos(final Set<TuringRule> entities) {
        return entities.stream().map(this::toDto).collect(toSet());
    }

    private TuringRuleDto toDto(final TuringRule entity) {
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
