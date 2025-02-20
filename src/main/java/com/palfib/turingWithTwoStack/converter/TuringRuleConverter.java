package com.palfib.turingWithTwoStack.converter;

import com.palfib.turingWithTwoStack.dto.TuringRuleDto;
import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.enums.Direction;
import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import com.palfib.turingWithTwoStack.entity.turing.TuringRule;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

@Component
public class TuringRuleConverter {

    private final MachineStateConverter machineStateConverter;

    public TuringRuleConverter(final MachineStateConverter machineStateConverter) {
        this.machineStateConverter = machineStateConverter;
    }

    public Set<TuringRule> fromDtos(final TuringMachine turingMachine, final Set<TuringRuleDto> dtos) {
        return dtos.stream().map(dto -> fromDto(turingMachine, dto)).collect(toSet());
    }

    private TuringRule fromDto(final TuringMachine turingMachine, final TuringRuleDto dto) {
        return TuringRule.builder()
                .id(dto.getId())
                .fromState(machineStateConverter.fromDto(turingMachine, dto.getFromState()))
                .toState(machineStateConverter.fromDto(turingMachine, dto.getToState()))
                .readCharacter(dto.getReadCharacter())
                .writeCharacter(dto.getWriteCharacter())
                .direction(Direction.valueOf(dto.getDirection()))
                .machine(turingMachine)
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
                .fromState(machineStateConverter.toDto(entity.getFromState()))
                .toState(machineStateConverter.toDto(entity.getToState()))
                .readCharacter(entity.getReadCharacter())
                .writeCharacter(entity.getWriteCharacter())
                .direction(entity.getDirection().name())
                .created(entity.getCreated())
                .build();
    }
}
