package com.palfib.turingWithTwoStack.service;

import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import com.palfib.turingWithTwoStack.exception.ValidationException;
import com.palfib.turingWithTwoStack.repository.TuringMachineRepository;
import lombok.val;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TuringMachineService {

    private final TuringMachineRepository turingMachineRepository;

    public TuringMachineService(final TuringMachineRepository turingMachineRepository) {
        this.turingMachineRepository = turingMachineRepository;
    }

    public TuringMachine findById(final Long id) throws ValidationException {
        final Optional<TuringMachine> turingMachine = turingMachineRepository.findById(id);
        if (!turingMachine.isPresent()) {
            throw new ValidationException(String.format("There is no turing machine with given ID (%s)", id));
        }
        return turingMachine.get();
    }

    public TuringMachine getAnBnCnMachine() {
        return findByName("AnBnCnMachine").get();
    }

    private Optional<TuringMachine> findByName(final String name) {
        val example = Example.of(TuringMachine.builder().name(name).build());
        return turingMachineRepository.findOne(example);
    }

    public List<TuringMachine> findAll() {
        return turingMachineRepository.findAll();
    }

    public TuringMachine save(final TuringMachine turingMachine) {
        turingMachine.getStates().forEach(state -> state.setTuringMachine(turingMachine));
        turingMachine.getRules().forEach(rule -> rule.setMachine(turingMachine));
        return turingMachineRepository.save(turingMachine);
    }

    public void delete(final TuringMachine turingMachine) {
        turingMachineRepository.delete(turingMachine);
    }

}
