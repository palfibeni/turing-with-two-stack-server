package com.palfib.turingWithTwoStack.service.converter;

import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import com.palfib.turingWithTwoStack.dto.TuringMachineDto;
import lombok.val;

public class TuringMachineConverter {

    public static TuringMachine fromDto(final TuringMachineDto dto) {
        val states = MachineStateConverter.fromDtos(dto.getStates());
        return TuringMachine.builder()
                .inputCharacters(dto.getTapeCharacters())
                .states(states)
                .rules(TuringRuleConverter.fromDtos(dto.getRules(), states))
                .build();
    }

    public static TuringMachineDto toDto(final TuringMachine entity) {
        return TuringMachineDto.builder()
                .tapeCharacters(entity.getInputCharacters())
                .states(MachineStateConverter.toDtos(entity.getStates()))
                .rules(TuringRuleConverter.toDtos(entity.getRules()))
                .build();
    }
}
