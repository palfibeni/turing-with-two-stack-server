package com.palfib.turingWithTwoStack.service.converter;

import com.palfib.turingWithTwoStack.dto.TuringMachineDto;
import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class TuringMachineConverter {

    private final MachineStateConverter machineStateConverter;

    private final TuringRuleConverter turingRuleConverter;

    public TuringMachineConverter(final MachineStateConverter machineStateConverter,
                                  final TuringRuleConverter turingRuleConverter){
        this.machineStateConverter = machineStateConverter;
        this.turingRuleConverter = turingRuleConverter;
    }

    public TuringMachine fromDto(final TuringMachineDto dto) {
        val states = machineStateConverter.fromDtos(dto.getStates());
        return TuringMachine.builder()
                .inputCharacters(dto.getTapeCharacters())
                .states(states)
                .rules(turingRuleConverter.fromDtos(dto.getRules(), states))
                .build();
    }

    public TuringMachineDto toDto(final TuringMachine entity) {
        return TuringMachineDto.builder()
                .tapeCharacters(entity.getInputCharacters())
                .states(machineStateConverter.toDtos(entity.getStates()))
                .rules(turingRuleConverter.toDtos(entity.getRules()))
                .build();
    }
}
