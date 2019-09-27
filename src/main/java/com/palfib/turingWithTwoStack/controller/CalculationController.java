package com.palfib.turingWithTwoStack.controller;

import com.palfib.turingWithTwoStack.dto.CalculationDto;
import com.palfib.turingWithTwoStack.dto.CalculationInputDto;
import com.palfib.turingWithTwoStack.exception.NoValidRunException;
import com.palfib.turingWithTwoStack.exception.ValidationException;
import com.palfib.turingWithTwoStack.service.CalculationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RequestMapping("/api")
@RestController
public class CalculationController {


    private final CalculationService calculationService;

    public CalculationController(final CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @PostMapping(path = "/calculate")
    @ResponseBody
    public CalculationDto calculate(
            final @RequestBody CalculationInputDto calculationInputDto) {
        try {
            return calculationService.calculate(calculationInputDto.getTuringMachine(), calculationInputDto.getInput());
        } catch (ValidationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        } catch (NoValidRunException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
}
