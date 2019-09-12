package com.palfib.turingWithTwoStack.service.converter;

import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import com.palfib.turingWithTwoStack.dto.TuringMachineDto;
import lombok.val;

import static java.util.stream.Collectors.toSet;

public class TuringMachineConverter {

    public static TuringMachine fromDto(final TuringMachineDto dto) {
        val states = MachineStateConverter.fromDtos(dto.getStates());
        val startState = states.stream()
                .filter(state -> state.getName().equals(dto.getStartState()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("StartState cannot be empty!"));
        val acceptStates = states.stream()
                .filter(state -> dto.getAcceptStates().contains(state.getName()))
                .collect(toSet());
        val declineState = states.stream()
                .filter(state -> dto.getDeclineStates().contains(state.getName()))
                .collect(toSet());
        return TuringMachine.builder()
                .inputCharacters(dto.getTapeCharacters())
                .states(states)
                .startState(startState)
                .acceptStates(acceptStates)
                .declineStates(declineState)
                .rules(TuringRuleConverter.fromDtos(dto.getRules(), states))
                .build();
    }

    public static TuringMachineDto toDto(final TuringMachine entity) {
        return TuringMachineDto.builder()
                .tapeCharacters(entity.getInputCharacters())
                .states(MachineStateConverter.toDtos(entity.getStates()))
                .startState(entity.getStartState().getName())
                .acceptStates(MachineStateConverter.toDtos(entity.getAcceptStates()))
                .declineStates(MachineStateConverter.toDtos(entity.getDeclineStates()))
                .build();
    }
}
