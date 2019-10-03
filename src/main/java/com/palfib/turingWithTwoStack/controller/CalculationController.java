package com.palfib.turingWithTwoStack.controller;

import com.palfib.turingWithTwoStack.dto.CalculationDto;
import com.palfib.turingWithTwoStack.dto.CalculationInputDto;
import com.palfib.turingWithTwoStack.exception.NoValidRunException;
import com.palfib.turingWithTwoStack.exception.ValidationException;
import com.palfib.turingWithTwoStack.service.CalculationService;
import com.palfib.turingWithTwoStack.converter.TuringMachineConverter;
import com.palfib.turingWithTwoStack.validator.CalculationValidator;
import com.palfib.turingWithTwoStack.validator.TuringMachineValidator;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RequestMapping("/api")
@RestController
public class CalculationController {

    private final TuringMachineValidator turingMachineValidator;

    private final CalculationValidator calculationValidator;

    private final TuringMachineConverter turingMachineConverter;

    private final CalculationService calculationService;

    public CalculationController(final TuringMachineValidator turingMachineValidator,
                                 final CalculationValidator calculationValidator,
                                 final TuringMachineConverter turingMachineConverter,
                                 final CalculationService calculationService) {
        this.turingMachineValidator = turingMachineValidator;
        this.calculationValidator = calculationValidator;
        this.turingMachineConverter = turingMachineConverter;
        this.calculationService = calculationService;
    }

    @PostMapping(path = "/calculate")
    @ResponseBody
    public CalculationDto calculate(
            final @RequestBody CalculationInputDto calculationInputDto) {
        try {
            val turingMachineDto = calculationInputDto.getTuringMachine();
            turingMachineValidator.validateTuringMachine(turingMachineDto);
            val input = calculationInputDto.getInput();
            calculationValidator.validateCalculation(turingMachineDto, input);
            return calculationService.calculate(turingMachineConverter.fromDto(turingMachineDto), input);
        } catch (ValidationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        } catch (NoValidRunException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
}
