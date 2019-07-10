package com.palfib.turingWithTwoStack.converter.turingMachine;

import com.palfib.turingWithTwoStack.converter.StateConverter;
import com.palfib.turingWithTwoStack.entity.turingMachine.TuringMachine;
import com.palfib.turingWithTwoStack.dto.turingMachine.TuringMachineDto;
import lombok.val;

import java.util.ArrayList;

import static java.util.stream.Collectors.toList;

public class TuringMachineConverter {

    public static TuringMachine fromDto(final TuringMachineDto dto) {
        val states = StateConverter.fromDtos(dto.getStates());
        val startState = states.stream()
                .filter(state -> state.getName().equals(dto.getStartState()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("StartState cannot be empty!"));
        val acceptStates = states.stream()
                .filter(state -> dto.getAcceptStates().contains(state.getName()))
                .collect(toList());
        return TuringMachine.builder()
                .inputCharacters(dto.getInputCharacters())
                .tapeCharacters(dto.getTapeCharacters())
                .states(states)
                .startState(startState)
                .acceptStates(acceptStates)
                .rules(TuringRuleConverter.fromDtos(dto.getRules(), states))
                .build();
    }

    public static TuringMachineDto toDto(final TuringMachine entity) {
        return TuringMachineDto.builder()
                .inputCharacters(entity.getInputCharacters())
                .tapeCharacters(entity.getTapeCharacters())
                .states(StateConverter.toDtos(entity.getStates()))
                .startState(entity.getStartState().getName())
                .acceptStates(StateConverter.toDtos(entity.getAcceptStates()))
                .rules(new ArrayList<>())
                .build();
    }
}
