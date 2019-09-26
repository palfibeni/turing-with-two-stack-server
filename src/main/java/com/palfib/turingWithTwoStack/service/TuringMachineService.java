package com.palfib.turingWithTwoStack.service;

import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import com.palfib.turingWithTwoStack.repository.TuringMachineRepository;
import lombok.val;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;

@Service
public class TuringMachineService {

    private final TuringMachineRepository turingMachineRepository;

    public TuringMachineService(final TuringMachineRepository turingMachineRepository) {
        this.turingMachineRepository = turingMachineRepository;
    }

    public Optional<TuringMachine> findById(final Long id) {
        return turingMachineRepository.findById(id);
    }

    public TuringMachine getAnBnCnMachine() {
        return findByName("AnBnCnMachine").get();
    }

    public Optional<TuringMachine> findByName(final String name) {
        val example = Example.of(TuringMachine.builder().name(name).build());
        return turingMachineRepository.findOne(example);
    }

    public List<TuringMachine> findAll() {
        return newArrayList(turingMachineRepository.findAll());
    }

}
