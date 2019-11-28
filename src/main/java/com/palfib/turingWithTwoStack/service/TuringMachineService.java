package com.palfib.turingWithTwoStack.service;

import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import com.palfib.turingWithTwoStack.entity.turing.TuringRule;
import com.palfib.turingWithTwoStack.exception.ValidationException;
import com.palfib.turingWithTwoStack.repository.MachineStateRepository;
import com.palfib.turingWithTwoStack.repository.TuringMachineRepository;
import com.palfib.turingWithTwoStack.repository.TuringRuleRepository;
import liquibase.util.CollectionUtil;
import lombok.val;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

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
        val oldRules = turingMachine.getId() != null ? turingMachineRepository.getOne(turingMachine.getId()).getRules() : new HashSet<TuringRule>();
        val newTuringMachine =  turingMachineRepository.saveAndFlush(turingMachine);
        val newRules = turingMachine.getRules().stream().map(rule -> {
            rule.setFromState(getSavedState(newTuringMachine, rule, TuringRule::getFromState));
            rule.setToState(getSavedState(newTuringMachine, rule, TuringRule::getToState));
            return turingRuleRepository.save(rule);
        }).collect(toSet());
        if (turingMachine.getId() != null) {
            oldRules.removeAll(newRules);
            oldRules.forEach(turingRuleRepository::delete);
        }
        return newTuringMachine;
    }

    private MachineState getSavedState(TuringMachine newTuringMachine, TuringRule rule, Function<TuringRule, MachineState> machineStateGetter) {
        return newTuringMachine.getStates()
                .stream()
                .filter(state -> state.getName().equals(machineStateGetter.apply(rule).getName()))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    public void delete(final TuringMachine turingMachine) {
        turingMachineRepository.delete(turingMachine);
    }

}
