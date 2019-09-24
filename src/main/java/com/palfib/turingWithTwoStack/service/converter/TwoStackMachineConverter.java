package com.palfib.turingWithTwoStack.service.converter;

import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import com.palfib.turingWithTwoStack.entity.twoStack.TwoStackMachine;
import org.springframework.stereotype.Component;

@Component
public class TwoStackMachineConverter {

    private final TwoStackRuleConverter twoStackRuleConverter;

    public TwoStackMachineConverter(final TwoStackRuleConverter twoStackRuleConverter){
        this.twoStackRuleConverter = twoStackRuleConverter;
    }

    public TwoStackMachine fromTuringMachine(final TuringMachine turingMachine) {
        return TwoStackMachine.builder()
                .inputCharacters(turingMachine.getInputCharacters())
                .states(turingMachine.getStates())
                .rules(twoStackRuleConverter.fromTuringRules(turingMachine.getRules()))
                .build();
    }

}
