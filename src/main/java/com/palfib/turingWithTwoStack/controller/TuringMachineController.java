package com.palfib.turingWithTwoStack.controller;

import com.palfib.turingWithTwoStack.dto.TuringMachineDto;
import com.palfib.turingWithTwoStack.exception.ValidationException;
import com.palfib.turingWithTwoStack.service.TuringMachineService;
import com.palfib.turingWithTwoStack.converter.TuringMachineConverter;
import com.palfib.turingWithTwoStack.validator.TuringMachineValidator;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RequestMapping("/api")
@RestController
public class TuringMachineController {

    private final TuringMachineValidator turingMachineValidator;

    private final TuringMachineConverter turingMachineConverter;

    private final TuringMachineService turingMachineService;

    TuringMachineController(final TuringMachineValidator turingMachineValidator,
                            final TuringMachineConverter turingMachineConverter,
                            final TuringMachineService turingMachineService) {
        this.turingMachineValidator = turingMachineValidator;
        this.turingMachineConverter = turingMachineConverter;
        this.turingMachineService = turingMachineService;
    }


    @GetMapping(path = "/turing-machines", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<TuringMachineDto>> getTuringMachines() {
        val turingMachines = turingMachineService.findAll();
        return ResponseEntity.ok(turingMachineConverter.toDtos(turingMachines));
    }

    @GetMapping(path = "/turing-machine/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TuringMachineDto> getTuringMachine(@PathVariable(value = "id") Long id) {
        try {
            val turingMachine = turingMachineService.findById(id);
            return ResponseEntity.ok(turingMachineConverter.toDto(turingMachine));
        } catch (ValidationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @GetMapping(path = "/an-bn-cn-turing-machine", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TuringMachineDto> getAnBnCnTuringMachine() {
        val anBnCnMachine = turingMachineService.getAnBnCnMachine();
        return ResponseEntity.ok(turingMachineConverter.toDto(anBnCnMachine));
    }

    /**
     * Creates or Updates a Turing machine
     * @return saved Turing machine
     */
    @PostMapping(path= "/turing-machine", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TuringMachineDto> saveTuringMachine(final @RequestBody TuringMachineDto turingMachineDto) {
        try {
            turingMachineValidator.validateTuringMachine(turingMachineDto);
            val saved = turingMachineService.save(turingMachineConverter.fromDto(turingMachineDto));
            return ResponseEntity.ok(turingMachineConverter.toDto(saved));
        } catch (ValidationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

}