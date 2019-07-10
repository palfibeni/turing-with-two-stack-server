package com.palfib.turingWithTwoStack.service;

import com.palfib.turingWithTwoStack.converter.turingMachine.TuringMachineConverter;
import com.palfib.turingWithTwoStack.dto.turingMachine.TuringMachineDto;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class TuringMachineService {

    public void calculate(final TuringMachineDto turingMachineDto, final String input) {
        val turingMachine = TuringMachineConverter.fromDto(turingMachineDto);
        turingMachine.initTape(input);
    }
}
