package com.palfib.turingWithTwoStack.service;

import com.palfib.turingWithTwoStack.dto.TuringMachineDto;
import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import com.palfib.turingWithTwoStack.exception.ValidationException;
import com.palfib.turingWithTwoStack.repository.TuringMachineRepository;
import com.palfib.turingWithTwoStack.service.converter.TuringMachineConverter;
import com.palfib.turingWithTwoStack.service.validator.TuringMachineValidator;
import lombok.val;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;

@Service
public class TuringMachineService {

    private final TuringMachineValidator turingMachineValidator;

    private final TuringMachineConverter turingMachineConverter;

    private final TuringMachineRepository turingMachineRepository;

    public TuringMachineService(final TuringMachineValidator turingMachineValidator,
                                final TuringMachineConverter turingMachineConverter,
                                final TuringMachineRepository turingMachineRepository) {
        this.turingMachineValidator = turingMachineValidator;
        this.turingMachineConverter = turingMachineConverter;
        this.turingMachineRepository = turingMachineRepository;
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

    private Optional<TuringMachine> findByName(final String name) {
        val example = Example.of(TuringMachine.builder().name(name).build());
        return turingMachineRepository.findOne(example);
    }

    public List<TuringMachineDto> findAll() {
        return turingMachineConverter.toDtos(newArrayList(turingMachineRepository.findAll()));
    }

    public TuringMachineDto save(final TuringMachineDto turingMachineDto) throws ValidationException {
        turingMachineValidator.validateTuringMachine(turingMachineDto);
        val saved = turingMachineRepository.save(turingMachineConverter.fromDto(turingMachineDto));
        return turingMachineConverter.toDto(saved);
    }

    public void delete(final TuringMachineDto dto) {
        turingMachineRepository.delete(turingMachineConverter.fromDto(dto));
    }

}
