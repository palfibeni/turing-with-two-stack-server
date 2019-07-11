package com.palfib.turingWithTwoStack.converter;

import com.palfib.turingWithTwoStack.entity.MachineState;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class MachineStateConverter {

    public static Set<MachineState> fromDtos(final Set<String> dtos) {
        return dtos.stream().map(MachineState::new).collect(toSet());
    }

    public static Set<String> toDtos(final Set<MachineState> entities) {
        return entities.stream().map(MachineState::getName).collect(toSet());
    }
}
