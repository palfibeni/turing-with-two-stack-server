package com.palfib.turingWithTwoStack.service;

import com.palfib.turingWithTwoStack.dto.CalculationDto;
import com.palfib.turingWithTwoStack.entity.turing.TuringCondition;
import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import com.palfib.turingWithTwoStack.entity.twoStack.TwoStackCondition;
import com.palfib.turingWithTwoStack.entity.twoStack.TwoStackMachine;
import com.palfib.turingWithTwoStack.exception.NoValidRunException;
import com.palfib.turingWithTwoStack.service.calculator.TuringCalculator;
import com.palfib.turingWithTwoStack.service.calculator.TwoStackCalculator;
import com.palfib.turingWithTwoStack.converter.ConditionConverter;
import com.palfib.turingWithTwoStack.service.converter.TwoStackMachineConverter;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CalculationService {

    private final TwoStackMachineConverter twoStackMachineConverter;

    private final ConditionConverter conditionConverter;

    public CalculationService(final TwoStackMachineConverter twoStackMachineConverter,
                              final ConditionConverter conditionConverter) {
        this.twoStackMachineConverter = twoStackMachineConverter;
        this.conditionConverter = conditionConverter;
    }

    public CalculationDto calculate(final TuringMachine turingMachine, final String input) {
        val turingConditions = turingCalculate(turingMachine, input);
        val twoStackMachine = twoStackMachineConverter.fromTuringMachine(turingMachine);
        val twoStackConditions = twoStackCalculate(twoStackMachine, input);
        return CalculationDto.builder()
                .turingConditions(conditionConverter.toDtos(turingConditions))
                .twoStackConditions(conditionConverter.toDtos(twoStackConditions))
                .build();
    }

    private List<TuringCondition> turingCalculate(final TuringMachine turingMachine, final String input) {
        val turingCalculator = new TuringCalculator(turingMachine);
        val result = turingCalculator.calculate(input);
        if (result == null) {
            // TODO exception handling
            throw new NoValidRunException(input);
        }
        return result;
    }


    private List<TwoStackCondition> twoStackCalculate(final TwoStackMachine twoStackMachine, final String input) {
        val twoStackCalculator = new TwoStackCalculator(twoStackMachine);
        val result = twoStackCalculator.calculate(input);
        if (result == null) {
            return Collections.emptyList();
        }
        return result;
    }
}
