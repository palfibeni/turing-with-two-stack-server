package com.palfib.turingWithTwoStack.service;

import com.palfib.turingWithTwoStack.dto.TuringMachineDto;
import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import com.palfib.turingWithTwoStack.exception.ValidationException;
import com.palfib.turingWithTwoStack.repository.TuringMachineRepository;
import com.palfib.turingWithTwoStack.service.converter.TuringMachineConverter;
import lombok.val;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;

@Service
public class TuringMachineService {

    private final TuringMachineRepository turingMachineRepository;

    private final TuringMachineConverter turingMachineConverter;

    public TuringMachineService(final TuringMachineRepository turingMachineRepository,
                                final TuringMachineConverter turingMachineConverter) {
        this.turingMachineRepository = turingMachineRepository;
        this.turingMachineConverter = turingMachineConverter;
    }

    public TuringMachineDto findById(final Long id) throws ValidationException {
        final Optional<TuringMachine> turingMachine = turingMachineRepository.findById(id);
        if (!turingMachine.isPresent()) {
            throw new ValidationException(String.format("There is no turing machine with given ID (%s)", id));
        }
        return turingMachineConverter.toDto(turingMachine.get());
    }

    public TuringMachineDto getAnBnCnMachine() {
        return turingMachineConverter.toDto(findByName("AnBnCnMachine").get());
    }

    public Optional<TuringMachine> findByName(final String name) {
        val example = Example.of(TuringMachine.builder().name(name).build());
        return turingMachineRepository.findOne(example);
    }

    public List<TuringMachineDto> findAll() {
        return turingMachineConverter.toDtos(newArrayList(turingMachineRepository.findAll()));
    }

}
