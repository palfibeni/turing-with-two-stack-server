package com.palfib.turingWithTwoStack.service.validator;

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

import static com.palfib.turingWithTwoStack.entity.Condition.EMPTY;
import static java.util.stream.Collectors.toSet;

@Component
public class CalculationValidator {

    public void validateCalculation(final TuringMachineDto turingMachineDto, final String input) throws ValidationException {
        val errors = new ArrayList<String>();

        validateInputContainsEmpty(input, errors);
        validateCharactersInInput(turingMachineDto, input, errors);
        validateStates(turingMachineDto, errors);
        validateStatesInRules(turingMachineDto, errors);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private void validateInputContainsEmpty(final String input, final List<String> errors) {
        val hasEmptyChar = input.chars()
                .mapToObj(ch -> (char) ch)
                .anyMatch(EMPTY::equals);
        if (hasEmptyChar) {
            errors.add("Input cannot contain the special character '_'!\n");
        }
    }

    private void validateCharactersInInput(final TuringMachineDto turingMachineDto, final String input, final List<String> errors) {
        val characters = turingMachineDto.getTapeCharacters();
        val inputCharsUnknown = input.chars()
                .mapToObj(ch -> (char) ch)
                .filter(ch -> !characters.contains(ch))
                .collect(toSet());
        if (!inputCharsUnknown.isEmpty()) {
            errors.add(String.format("The input contains characters not defined in tape characters. (%s)\n", inputCharsUnknown.toString()));
        }
    }

    private void validateStates(final TuringMachineDto turingMachineDto, final List<String> errors) {
        val states = turingMachineDto.getStates();
        val unknownAcceptStates = turingMachineDto.getAcceptStates().stream().filter(state -> states.contains(state)).collect(toSet());
        if (!unknownAcceptStates.isEmpty()) {
            errors.add(String.format("There is unknown states in acceptStates: %s\n", unknownAcceptStates.toString()));
        }
        val unknownDeclineStates = turingMachineDto.getDeclineStates().stream().filter(state -> states.contains(state)).collect(toSet());
        if (!unknownDeclineStates.isEmpty()) {
            errors.add(String.format("There is unknown states in declineStates: %s\n", unknownDeclineStates.toString()));
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

    private Stream<String> getUnknownStateStreamFromRule(final TuringMachineDto turingMachineDto, final Set<String> states, final Function<TuringRuleDto, String> getState) {
        return turingMachineDto.getRules().stream()
                .filter(rule -> !states.contains(getState.apply(rule)))
                .map(getState);
    }
}
