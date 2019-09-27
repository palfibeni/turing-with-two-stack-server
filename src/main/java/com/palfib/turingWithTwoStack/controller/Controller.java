package com.palfib.turingWithTwoStack.controller;

import com.palfib.turingWithTwoStack.dto.CalculationDto;
import com.palfib.turingWithTwoStack.dto.CalculationInputDto;
import com.palfib.turingWithTwoStack.dto.TuringMachineDto;
import com.palfib.turingWithTwoStack.exception.NoValidRunException;
import com.palfib.turingWithTwoStack.exception.ValidationException;
import com.palfib.turingWithTwoStack.repository.TuringMachineRepository;
import com.palfib.turingWithTwoStack.service.CalculationService;
import com.palfib.turingWithTwoStack.service.TuringMachineService;
import com.palfib.turingWithTwoStack.service.converter.TuringMachineConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RequestMapping("/api")
@RestController
public class Controller {

    private final CalculationService calculationService;


    private final TuringMachineService turingMachineService;

    Controller(final CalculationService calculationService, final TuringMachineService turingMachineService) {
        this.calculationService = calculationService;
        this.turingMachineService = turingMachineService;
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


    @GetMapping(path = "/turing-machines", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<TuringMachineDto>> getTuringMachines() {
        return ResponseEntity.ok(turingMachineService.findAll());
    }

    @GetMapping(path = "/turing-machine/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TuringMachineDto> getTuringMachine(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(turingMachineService.findById(id));
        } catch (ValidationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @GetMapping(path = "/an-bn-cn-turing-machine", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TuringMachineDto> getAnBnCnTuringMachine() {
        return ResponseEntity.ok(turingMachineService.getAnBnCnMachine());
    }

}