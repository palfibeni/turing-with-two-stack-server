package com.palfib.turingWithTwoStack.service;

import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import com.palfib.turingWithTwoStack.exception.ValidationException;
import com.palfib.turingWithTwoStack.repository.MachineStateRepository;
import com.palfib.turingWithTwoStack.repository.TuringMachineRepository;
import com.palfib.turingWithTwoStack.repository.TuringRuleRepository;
import lombok.val;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TuringMachineService {

    private final TuringMachineRepository turingMachineRepository;
    private final MachineStateRepository machineStateRepository;
    private final TuringRuleRepository turingRuleRepository;

    public TuringMachineService(final TuringMachineRepository turingMachineRepository,
                                final MachineStateRepository machineStateRepository,
                                final TuringRuleRepository turingRuleRepository) {
        this.turingMachineRepository = turingMachineRepository;
        this.machineStateRepository = machineStateRepository;
        this.turingRuleRepository = turingRuleRepository;
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
        if(turingMachine.getId() != null) {
            val oldTuringMachine = turingMachineRepository.getOne(turingMachine.getId());
            oldTuringMachine.getStates().forEach(machineStateRepository::delete);
            oldTuringMachine.getRules().forEach(turingRuleRepository::delete);
        }
        val newTuringMachine =  turingMachineRepository.save(turingMachine);
        turingMachine.getStates().forEach(state -> state.setTuringMachine(newTuringMachine));
        turingMachine.getStates().forEach(machineStateRepository::save);
        turingMachine.getRules().forEach(rule -> rule.setMachine(newTuringMachine));
        turingMachine.getRules().forEach(turingRuleRepository::save);
        return newTuringMachine;
    }

    public void delete(final TuringMachine turingMachine) {
        turingMachineRepository.delete(turingMachine);
    }

}
