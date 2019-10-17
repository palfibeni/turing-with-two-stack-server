package com.palfib.turingWithTwoStack.validator;

import com.palfib.turingWithTwoStack.dto.MachineStateDto;
import com.palfib.turingWithTwoStack.dto.TuringMachineDto;
import com.palfib.turingWithTwoStack.dto.TuringRuleDto;
import com.palfib.turingWithTwoStack.exception.ValidationException;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@Component
public class TuringMachineValidator {

    public void validateTuringMachine(final TuringMachineDto turingMachineDto) throws ValidationException {
        val errors = new ArrayList<String>();
        
        validateStates(errors, turingMachineDto.getStates());
        validateStatesInRules(turingMachineDto, errors);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private void validateStates(final List<String> errors, Set<MachineStateDto> states) {
        if (states.stream().noneMatch(MachineStateDto::isStart)) {
            errors.add("There is no start state!\n");
        }
    }

    private void validateStatesInRules(final TuringMachineDto turingMachineDto, final List<String> errors) {
        val states = turingMachineDto.getStates().stream().map(MachineStateDto::getId).collect(toSet());
        val unknownFromStateStream = getUnknownStateStreamFromRule(turingMachineDto, states, TuringRuleDto::getFromState);
        val unknownToStateStream = getUnknownStateStreamFromRule(turingMachineDto, states, TuringRuleDto::getToState);
        val statesUnknown = Stream.concat(unknownFromStateStream, unknownToStateStream).collect(toSet());
        if (!statesUnknown.isEmpty()) {
            errors.add(String.format("There is unknown state ids in some rules: %s\n", statesUnknown.toString()));
        }
    }

    private Stream<Long> getUnknownStateStreamFromRule(
            final TuringMachineDto turingMachineDto, final Set<Long> states, final Function<TuringRuleDto, Long> getState) {
        return turingMachineDto.getRules().stream()
                .filter(rule -> !states.contains(getState.apply(rule)))
                .map(getState);
    }
}
