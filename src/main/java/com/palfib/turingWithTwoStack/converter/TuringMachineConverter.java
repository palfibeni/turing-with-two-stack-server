package com.palfib.turingWithTwoStack.converter;

import com.palfib.turingWithTwoStack.dto.TuringMachineDto;
import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

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
                .id(dto.getId())
                .name(dto.getName())
                .inputCharacters(dto.getTapeCharacters())
                .states(states)
                .rules(turingRuleConverter.fromDtos(dto.getRules(), states))
                .build();
    }

    public List<TuringMachineDto> toDtos(final List<TuringMachine> turingMachines) {
        return turingMachines.stream().map(this::toDto).collect(toList());
    }

    public TuringMachineDto toDto(final TuringMachine entity) {
        return TuringMachineDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .tapeCharacters(entity.getInputCharacters())
                .states(machineStateConverter.toDtos(entity.getStates()))
                .rules(turingRuleConverter.toDtos(entity.getRules()))
                .created(entity.getCreated())
                .build();
    }
}
