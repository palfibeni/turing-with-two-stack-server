package com.palfib.turingWithTwoStack.service;

import com.palfib.turingWithTwoStack.converter.TuringMachineConverter;
import com.palfib.turingWithTwoStack.dto.TuringMachineDto;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class TuringMachineService {

    public void calculate(final TuringMachineDto turingMachineDto, final String input) {
        val turingMachine = TuringMachineConverter.fromDto(turingMachineDto);
    }
}
