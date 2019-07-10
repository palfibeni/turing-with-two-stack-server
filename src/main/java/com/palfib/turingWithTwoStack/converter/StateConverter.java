package com.palfib.turingWithTwoStack.converter;

import com.palfib.turingWithTwoStack.entity.State;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class StateConverter {

    public static List<State> fromDtos(final List<String> dtos) {
        return IntStream.range(1, dtos.size() + 1)
                .mapToObj(index -> new State(index, dtos.get(index - 1)))
                .collect(toList());
    }

    public static List<String> toDtos(final List<State> entities) {
        return entities.stream().map(State::getName).collect(toList());
    }
}
