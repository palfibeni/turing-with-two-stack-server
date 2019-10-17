package com.palfib.turingWithTwoStack.validator;

import com.palfib.turingWithTwoStack.dto.TuringMachineDto;
import com.palfib.turingWithTwoStack.exception.ValidationException;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.palfib.turingWithTwoStack.entity.Condition.EMPTY;
import static java.util.stream.Collectors.toSet;

@Component
public class CalculationValidator {

    public void validateCalculation(final TuringMachineDto turingMachineDto, final String input) throws ValidationException {
        val errors = new ArrayList<String>();

        validateInputContainsEmpty(input, errors);
        validateCharactersInInput(turingMachineDto, input, errors);
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
}
