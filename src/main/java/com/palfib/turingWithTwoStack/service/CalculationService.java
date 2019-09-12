package com.palfib.turingWithTwoStack.service;

import com.palfib.turingWithTwoStack.exception.ValidationException;
import com.palfib.turingWithTwoStack.service.calculator.TuringCalculator;
import com.palfib.turingWithTwoStack.service.calculator.TwoStackCalculator;
import com.palfib.turingWithTwoStack.service.converter.ConditionConverter;
import com.palfib.turingWithTwoStack.service.converter.TuringMachineConverter;
import com.palfib.turingWithTwoStack.service.converter.TwoStackMachineConverter;
import com.palfib.turingWithTwoStack.dto.CalculationDto;
import com.palfib.turingWithTwoStack.dto.TuringMachineDto;
import com.palfib.turingWithTwoStack.entity.turing.TuringCondition;
import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import com.palfib.turingWithTwoStack.entity.twoStack.TwoStackCondition;
import com.palfib.turingWithTwoStack.entity.twoStack.TwoStackMachine;
import com.palfib.turingWithTwoStack.service.validator.CalculationValidator;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculationService {

    private final CalculationValidator calculationValidator;

    public CalculationService(final CalculationValidator calculationValidator) {
        this.calculationValidator = calculationValidator;
    }

    public CalculationDto calculate(final TuringMachineDto turingMachineDto, final String input) throws ValidationException {
        calculationValidator.validateCalculation(turingMachineDto, input);

        val turingMachine = TuringMachineConverter.fromDto(turingMachineDto);
        val turingConditions = turingCalculate(turingMachine, input);
        val twoStackMachine = TwoStackMachineConverter.fromTuringMachine(turingMachine);
        val twoStackConditions = twoStackCalculate(twoStackMachine, input);
        return CalculationDto.builder()
                .turingConditions(ConditionConverter.toDtos(turingConditions))
                .twoStackCalculations(ConditionConverter.toDtos(twoStackConditions))
                .build();
    }

    private List<TuringCondition> turingCalculate(final TuringMachine turingMachine, final String input) {
        val turingCalculator = new TuringCalculator(turingMachine);
        val result = turingCalculator.calculate(input);
        if (result == null) {
            // TODO exception handling
            throw new RuntimeException("No valid run found.");
        }
        return result;
    }


    private List<TwoStackCondition> twoStackCalculate(final TwoStackMachine twoStackMachine, final String input) {
        val twoStackCalculator = new TwoStackCalculator(twoStackMachine);
        val result = twoStackCalculator.calculate(input);
        if (result == null) {
            // TODO exception handling
            throw new RuntimeException("No valid run found.");
        }
        return result;
    }
}
