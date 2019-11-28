package com.palfib.turingWithTwoStack.validator;

import com.palfib.turingWithTwoStack.dto.MachineStateDto;
import com.palfib.turingWithTwoStack.dto.TuringMachineDto;
import com.palfib.turingWithTwoStack.dto.TuringRuleDto;
import com.palfib.turingWithTwoStack.entity.MachineState;
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

        validateCharacters(errors, turingMachineDto.getTapeCharacters());
        validateStates(errors, turingMachineDto.getStates());
        validateStatesInRules(turingMachineDto, errors);
        turingMachineDto.getRules().forEach(rule -> validateRule(errors, rule));

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private void validateCharacters(final List<String> errors, Set<Character> tapeCharacters) {
        if (tapeCharacters.isEmpty()) {
            errors.add("Empty Character is not allowed!\n");
        }
        if (tapeCharacters.contains('_') || tapeCharacters.contains('#') || tapeCharacters.contains('*')) {
            errors.add("Reserved character is not allowed! ('_', '#', '*')\n");
        }
    }

    private void validateStates(final List<String> errors, Set<MachineStateDto> states) {
        val startStates = states.stream().filter(MachineStateDto::isStart).count();
        if (startStates == 0) {
            errors.add("There is no start state!\n");
        }
        if (startStates > 1) {
            errors.add("More than one start state!\n");
        }
        states.forEach(state -> validateState(errors, state));
    }

    private void validateState(final List<String> errors, final MachineStateDto state) {
        val name = state.getName();
        if (name == null || name.length() == 0 || name.trim().length() == 0) {
            errors.add("State name cannot be empty!\n");
        }
        if ("READ_INPUT_TO_LEFT".equals(name)) {
            errors.add("\"READ_INPUT_TO_LEFT\" is a reserved state name!\n");
        }
        if ("COPY_INPUT_TO_RIGHT".equals(name)) {
            errors.add("\"COPY_INPUT_TO_RIGHT\" is a reserved state name!\n");
        }
    }

    private void validateStatesInRules(final TuringMachineDto turingMachineDto, final List<String> errors) {
        val states = turingMachineDto.getStates();
        val unknownFromStateStream = getUnknownStateStreamFromRule(turingMachineDto, states, TuringRuleDto::getFromState);
        val unknownToStateStream = getUnknownStateStreamFromRule(turingMachineDto, states, TuringRuleDto::getToState);
        val statesUnknown = Stream.concat(unknownFromStateStream, unknownToStateStream).collect(toSet());
        if (!statesUnknown.isEmpty()) {
            errors.add(String.format("There is unknown states in some rules: %s\n", statesUnknown.toString()));
        }
    }

    private void validateRule(final List<String> errors, final TuringRuleDto rule) {
        if (rule.getToState() == null) {
            errors.add("Rule's toState field cannot be empty!\n");
        }
        if (rule.getReadCharacter() == null || rule.getReadCharacter().toString().length() == 0 || rule.getReadCharacter().toString().trim().length() == 0) {
            errors.add("Rule's readCharacter field cannot be empty!\n");
        }
        if (rule.getFromState() == null) {
            errors.add("Rule's fromState field cannot be empty!\n");
        }
        if (rule.getWriteCharacter() == null || rule.getWriteCharacter().toString().length() == 0 || rule.getWriteCharacter().toString().trim().length() == 0) {
            errors.add("Rule's writeCharacter field cannot be empty!\n");
        }
        if (rule.getDirection() == null) {
            errors.add("Rule's direction field cannot be empty!\n");
        }
    }

    private Stream<MachineStateDto> getUnknownStateStreamFromRule(
            final TuringMachineDto turingMachineDto, final Set<MachineStateDto> states, final Function<TuringRuleDto, MachineStateDto> getState) {
        return turingMachineDto.getRules().stream()
                .filter(rule -> !states.contains(getState.apply(rule)))
                .map(getState);
    }
}
