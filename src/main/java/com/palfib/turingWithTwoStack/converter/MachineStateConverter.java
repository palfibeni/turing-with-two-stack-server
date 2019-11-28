package com.palfib.turingWithTwoStack.converter;

import com.palfib.turingWithTwoStack.dto.MachineStateDto;
import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

@Component
public class MachineStateConverter {

    public Set<MachineState> fromDtos(final TuringMachine turingMachine, final Set<MachineStateDto> dtos) {
        return dtos.stream().map(dto -> fromDto(turingMachine, dto)).collect(toSet());
    }

    public MachineState fromDto(final TuringMachine turingMachine, final MachineStateDto dto) {
        return MachineState.builder()
                .id(dto.getId())
                .name(dto.getName())
                .start(dto.isStart())
                .accept(dto.isAccept())
                .decline(dto.isDecline())
                .turingMachine(turingMachine)
                .build();
    }

    public Set<MachineStateDto> toDtos(final Set<MachineState> entities) {
        return entities.stream().map(this::toDto).collect(toSet());
    }

    public MachineStateDto toDto(final MachineState entity) {
        return MachineStateDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .start(entity.isStart())
                .accept(entity.isAccept())
                .decline(entity.isDecline())
                .created(entity.getCreated())
                .build();
    }
}
