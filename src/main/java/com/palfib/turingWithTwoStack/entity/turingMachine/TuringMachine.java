package com.palfib.turingWithTwoStack.entity.turingMachine;

import com.palfib.turingWithTwoStack.entity.MachineState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TuringMachine {

    @Builder.Default
    private Set<Character> inputCharacters = new HashSet<>();

    @Builder.Default
    private Set<Character> tapeCharacters = new HashSet<>();

    @Builder.Default
    private Set<MachineState> states = new HashSet<>();

    private MachineState startState;

    @Builder.Default
    private Set<MachineState> acceptStates = new HashSet<>();

    @Builder.Default
    private Set<MachineState> declineStates = new HashSet<>();

    @Builder.Default
    private Set<TuringRule> rules = new HashSet<>();

    public TuringMachine(final TuringMachine turingMachine) {
        this.inputCharacters.addAll(turingMachine.inputCharacters);
        this.tapeCharacters.addAll(turingMachine.tapeCharacters);
        this.states.addAll(turingMachine.states);
        this.startState = turingMachine.startState;
        this.acceptStates.addAll(turingMachine.acceptStates);
        this.declineStates.addAll(turingMachine.declineStates);
        this.rules.addAll(turingMachine.rules);
    }
}
