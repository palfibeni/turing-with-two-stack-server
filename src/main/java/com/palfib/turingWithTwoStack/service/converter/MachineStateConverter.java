package com.palfib.turingWithTwoStack.service.converter;

import com.palfib.turingWithTwoStack.dto.MachineStateDto;
import com.palfib.turingWithTwoStack.entity.MachineState;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

@Component
public class MachineStateConverter {

    private Long stateId = 1L;

    public Set<MachineState> fromDtos(final Set<MachineStateDto> dtos) {
        stateId = 1L;
        return dtos.stream().map(this::fromDto).collect(toSet());
    }

    private MachineState fromDto(final MachineStateDto dto) {
        return MachineState.builder()
                .id(ofNullable(dto.getId()).orElse(stateId++))
                .name(dto.getName())
                .start(dto.isStart())
                .accept(dto.isAccept())
                .decline(dto.isDecline())
                .build();
    }

    public Set<MachineStateDto> toDtos(final Set<MachineState> entities) {
        return entities.stream().map(this::toDto).collect(toSet());
    }

    public MachineStateDto toDto(final MachineState dto) {
        return MachineStateDto.builder()
                .id(dto.getId())
                .name(dto.getName())
                .start(dto.isStart())
                .accept(dto.isAccept())
                .decline(dto.isDecline())
                .build();
    }
}
