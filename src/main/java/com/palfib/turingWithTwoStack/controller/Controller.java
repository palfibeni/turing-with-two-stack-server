package com.palfib.turingWithTwoStack.controller;

import com.palfib.turingWithTwoStack.dto.CalculationDto;
import com.palfib.turingWithTwoStack.dto.TuringMachineDto;
import com.palfib.turingWithTwoStack.exception.ValidationException;
import com.palfib.turingWithTwoStack.service.CalculationService;
import com.palfib.turingWithTwoStack.service.converter.TuringMachineConverter;
import com.palfib.turingWithTwoStack.service.defaults.AnBnCnTuringMachine;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotBlank;


@RequestMapping("/api")
@RestController
public class Controller {

    private CalculationService calculationService;

    Controller(final CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @RequestMapping(path = "/calculate", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CalculationDto> calculate(
            final @RequestParam(name = "turingMachine") @NotBlank TuringMachineDto turingMachine,
            final @RequestParam(name = "input") @NotBlank String input) {
        try {
            return ResponseEntity.ok(calculationService.calculate(turingMachine, input));
        } catch (ValidationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @RequestMapping(path = "/getAnBnCnTuringMachine", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TuringMachineDto> getAnBnCnTuringMachne() {
        return ResponseEntity.ok(TuringMachineConverter.toDto(AnBnCnTuringMachine.createAnBnCnMachine()));
    }
}