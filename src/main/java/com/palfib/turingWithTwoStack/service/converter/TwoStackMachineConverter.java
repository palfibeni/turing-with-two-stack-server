package com.palfib.turingWithTwoStack.service.converter;

import com.google.common.collect.Sets;
import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import com.palfib.turingWithTwoStack.entity.twoStack.TwoStackMachine;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class TwoStackMachineConverter {
    private static final String READ_INPUT_TO_LEFT = "READ_INPUT_TO_LEFT";
    private static final String COPY_INPUT_TO_RIGHT = "COPY_INPUT_TO_RIGHT";

    private final TwoStackRuleConverter twoStackRuleConverter;

    public TwoStackMachineConverter(final TwoStackRuleConverter twoStackRuleConverter){
        this.twoStackRuleConverter = twoStackRuleConverter;
    }

    public TwoStackMachine fromTuringMachine(final TuringMachine turingMachine) {
        val startRead = MachineState.builder().name(READ_INPUT_TO_LEFT).start(true).accept(false).decline(false).build();
        val copyToRight = MachineState.builder().name(COPY_INPUT_TO_RIGHT).start(false).accept(false).decline(false).build();
        val turingStart = turingMachine.getStartState();
        val states = Sets.newHashSet(turingMachine.getStates());
        states.remove(turingStart);
        turingStart.setStart(false);
        states.add(startRead);
        states.add(copyToRight);
        states.add(turingStart);
        val inputRules = twoStackRuleConverter.fromTuringTapeCharachters(startRead, copyToRight, turingStart, turingMachine.getInputCharacters());
        val convertedRules = twoStackRuleConverter.fromTuringRules(turingMachine.getRules());
        return TwoStackMachine.builder()
                .inputCharacters(turingMachine.getInputCharacters())
                .states(states)
                .rules(Sets.union(inputRules, convertedRules))
                .build();
    }

}
