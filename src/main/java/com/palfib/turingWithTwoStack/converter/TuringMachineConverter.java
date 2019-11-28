package com.palfib.turingWithTwoStack.converter;

import com.palfib.turingWithTwoStack.dto.TuringMachineDto;
import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import com.palfib.turingWithTwoStack.entity.turing.TuringRule;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

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
        val turingMachine = TuringMachine.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .inputCharacters(dto.getTapeCharacters())
                .build();
        val states = machineStateConverter.fromDtos(turingMachine, dto.getStates());
        turingMachine.setStates(states);
        val rules = turingRuleConverter.fromDtos(turingMachine, dto.getRules());
        turingMachine.setRules(rules);
        return turingMachine;
    }

    public List<TuringMachineDto> toDtos(final List<TuringMachine> turingMachines) {
        return turingMachines.stream().map(this::toDto).collect(toList());
    }

    public TuringMachineDto toDto(final TuringMachine entity) {
        return TuringMachineDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .tapeCharacters(entity.getInputCharacters())
                .states(machineStateConverter.toDtos(entity.getStates()))
                .rules(turingRuleConverter.toDtos(entity.getRules()))
                .created(entity.getCreated())
                .build();
    }
}
