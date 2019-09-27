package com.palfib.turingWithTwoStack.controller;

import com.palfib.turingWithTwoStack.dto.TuringMachineDto;
import com.palfib.turingWithTwoStack.exception.ValidationException;
import com.palfib.turingWithTwoStack.service.TuringMachineService;
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

    private final TuringMachineService turingMachineService;

    TuringMachineController(final TuringMachineService turingMachineService) {
        this.turingMachineService = turingMachineService;
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

    /**
     * Creates or Updates a Turing machine
     * @return saved Turing machine
     */
    @PostMapping(path= "/turing-machine", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TuringMachineDto> updateTuringMachine(final @RequestBody TuringMachineDto turingMachineDto) {
        try {
            val saved = turingMachineService.save(turingMachineDto);
            return ResponseEntity.ok(saved);
        } catch (ValidationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

}