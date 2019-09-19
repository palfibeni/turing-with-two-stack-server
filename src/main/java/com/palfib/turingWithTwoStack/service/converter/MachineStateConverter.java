package com.palfib.turingWithTwoStack.service.converter;

import com.palfib.turingWithTwoStack.dto.MachineStateDto;
import com.palfib.turingWithTwoStack.entity.MachineState;

import java.util.Set;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

public class MachineStateConverter {

    private static Long stateId = 1L;

    public static Set<MachineState> fromDtos(final Set<MachineStateDto> dtos) {
        stateId = 1L;
        return dtos.stream().map(MachineStateConverter::fromDto).collect(toSet());
    }

    private static MachineState fromDto(final MachineStateDto dto) {
        return MachineState.builder()
                .id(ofNullable(dto.getId()).orElse(stateId++))
                .name(dto.getName())
                .start(dto.isStart())
                .accept(dto.isAccept())
                .decline(dto.isDecline())
                .build();
    }

    public static Set<MachineStateDto> toDtos(final Set<MachineState> entities) {
        return entities.stream().map(MachineStateConverter::toDto).collect(toSet());
    }

    public static MachineStateDto toDto(final MachineState dto) {
        return MachineStateDto.builder()
                .id(dto.getId())
                .name(dto.getName())
                .start(dto.isStart())
                .accept(dto.isAccept())
                .decline(dto.isDecline())
                .build();
    }
}
