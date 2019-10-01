package com.palfib.turingWithTwoStack.entity.twoStack;

import com.palfib.turingWithTwoStack.entity.Machine;
import com.palfib.turingWithTwoStack.entity.MachineState;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Data
public class TwoStackMachine extends Machine {

    private Set<TwoStackRule> rules;

    private Set<MachineState> states;

    @Builder
    public TwoStackMachine(final Long id, final Set<Character> inputCharacters,
                           final Set<MachineState> states, final Set<TwoStackRule> rules, final Date created) {
        super(id, inputCharacters, created);
        this.rules = rules;
        this.states = states;
    }

    public MachineState getStartState() {
        // TODO validálni kéne
        return states.stream().filter(MachineState::isStart).findFirst().orElse(null);
    }

    public Set<MachineState> getAcceptStates() {
        return states.stream().filter(MachineState::isAccept).collect(toSet());
    }

    public Set<MachineState> getDeclineStates() {
        return states.stream().filter(MachineState::isDecline).collect(toSet());
    }

}
