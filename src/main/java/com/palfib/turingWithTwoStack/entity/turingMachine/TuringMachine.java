package com.palfib.turingWithTwoStack.entity.turingMachine;

import com.palfib.turingWithTwoStack.entity.State;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import org.hibernate.internal.util.Cloneable;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
public class TuringMachine extends Cloneable {

    private final List<Character> inputCharacters = new ArrayList<>();

    private final List<Character> tapeCharacters = new ArrayList<>();

    private final List<State> states = new ArrayList<>();

    private State startState;

    private State currentState;

    private final List<State> acceptStates = new ArrayList<>();

    private final List<TuringRule> rules = new ArrayList<>();

    private Tape tape;

    public void initTape(String input) {
        this.tape = new Tape(input);
    }

    @Override
    public TuringMachine clone() {
        val clone = new TuringMachine();
        clone.inputCharacters.addAll(this.inputCharacters);
        clone.tapeCharacters.addAll(this.tapeCharacters);
    }
}
