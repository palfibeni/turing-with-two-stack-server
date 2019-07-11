package com.palfib.turingWithTwoStack.calculator;

import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.enums.Direction;
import com.palfib.turingWithTwoStack.entity.turingMachine.Tape;
import com.palfib.turingWithTwoStack.entity.turingMachine.TuringMachine;
import com.palfib.turingWithTwoStack.entity.turingMachine.TuringRule;
import com.palfib.turingWithTwoStack.entity.turingMachine.TuringState;
import lombok.val;
import org.assertj.core.util.Sets;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class TuringCalculatorTest {

    private final Set<Character> characters = Sets.newLinkedHashSet('A', 'B', 'C');

    private final MachineState startState = new MachineState("START");
    private final MachineState middleState1 = new MachineState("MIDDLE1");
    private final MachineState middleState2 = new MachineState("MIDDLE2");
    private final MachineState acceptState1 = new MachineState("ACCEPT1");
    private final MachineState acceptState2 = new MachineState("ACCEPT2");
    private final MachineState declineState1 = new MachineState("DECLINE1");
    private final MachineState declineState2 = new MachineState("DECLINE2");

    private final Set<MachineState> states = Sets.newLinkedHashSet(startState,
            middleState1, middleState2,
            acceptState1, acceptState2,
            declineState1, declineState2);

    private TuringMachine turingMachine;

    private TuringCalculator turingCalculator;

    @Test
    public void acceptLinearAtStart() {
        this.turingMachine = createTuringMachine(Collections.singleton(startState), Sets.newHashSet(), Sets.newHashSet());

        final List<TuringState> result = new TuringCalculator(turingMachine).calculate("ABC");

        assertThat(result.size()).isEqualTo(1);

        val state = result.get(0);
        assertThat(state.getCurrentState()).isEqualTo(startState);
        assertThat(state.getTape().getCurrentPosition()).isEqualTo('A');
        assertThat(state.getTape().getCharactersAhead()).containsOnly('B', 'C');
    }

    @Test
    public void declineLinearAtStart() {
        this.turingMachine = createTuringMachine(Sets.newHashSet(), Collections.singleton(startState), Sets.newHashSet());

        final List<TuringState> result = new TuringCalculator(turingMachine).calculate("ABC");

        assertThat(result).isNull();
    }

    @Test
    public void acceptLinearWithOneStep() {
        final LinkedHashSet<TuringRule> rules = Sets.newLinkedHashSet(
                createRule(startState, 'A', Direction.STAY, acceptState1, 'X'));
        this.turingMachine = createTuringMachine(Collections.singleton(acceptState1), Sets.newHashSet(), rules);

        final List<TuringState> result = new TuringCalculator(turingMachine).calculate("ABC");

        assertThat(result.size()).isEqualTo(2);

        val state1 = result.get(0);
        assertThat(state1.getCurrentState()).isEqualTo(startState);
        assertThat(state1.getTape().getCurrentPosition()).isEqualTo('A');
        assertThat(state1.getTape().getCharactersAhead()).containsOnly('B', 'C');

        val state2 = result.get(1);
        assertThat(state2.getCurrentState()).isEqualTo(acceptState1);
        assertThat(state2.getTape().getCurrentPosition()).isEqualTo('X');
        assertThat(state2.getTape().getCharactersAhead()).containsOnly('B', 'C');
    }

    @Test
    public void declineLinearWithOneStep() {
        final LinkedHashSet<TuringRule> rules = Sets.newLinkedHashSet(
                createRule(startState, 'A', Direction.STAY, declineState1, 'X'));
        this.turingMachine = createTuringMachine(Sets.newHashSet(), Collections.singleton(declineState1), rules);

        final List<TuringState> result = new TuringCalculator(turingMachine).calculate("ABC");

        assertThat(result).isNull();

    }

    @Test
    public void acceptLinearAfterReadWholeInput() {
        final LinkedHashSet<TuringRule> rules = Sets.newLinkedHashSet(
                createRule(startState, 'A', Direction.FORWARD, middleState1, 'X'),
                createRule(middleState1, 'B', Direction.FORWARD, middleState1, 'X'),
                createRule(middleState1, 'C', Direction.FORWARD, middleState1, 'X'),
                createRule(middleState1, Tape.EMPTY, Direction.STAY, acceptState1, Tape.EMPTY));
        this.turingMachine = createTuringMachine(Collections.singleton(acceptState1), Sets.newHashSet(), rules);

        final List<TuringState> result = new TuringCalculator(turingMachine).calculate("ABC");

        assertThat(result.size()).isEqualTo(5);

        val state1 = result.get(0);
        assertThat(state1.getCurrentState()).isEqualTo(startState);
        assertThat(state1.getTape().getCharactersBehind()).isEmpty();
        assertThat(state1.getTape().getCurrentPosition()).isEqualTo('A');
        assertThat(state1.getTape().getCharactersAhead()).containsOnly('B', 'C');

        val state2 = result.get(1);
        assertThat(state2.getCurrentState()).isEqualTo(middleState1);
        assertThat(state2.getTape().getCharactersBehind()).containsOnly('X');
        assertThat(state2.getTape().getCurrentPosition()).isEqualTo('B');
        assertThat(state2.getTape().getCharactersAhead()).containsOnly('C');

        val state3 = result.get(2);
        assertThat(state3.getCurrentState()).isEqualTo(middleState1);
        assertThat(state2.getTape().getCharactersBehind()).containsOnly('X');
        assertThat(state3.getTape().getCurrentPosition()).isEqualTo('C');
        assertThat(state3.getTape().getCharactersAhead()).isEmpty();

        val state4 = result.get(3);
        assertThat(state4.getCurrentState()).isEqualTo(middleState1);
        assertThat(state2.getTape().getCharactersBehind()).containsOnly('X');
        assertThat(state4.getTape().getCurrentPosition()).isEqualTo(Tape.EMPTY);
        assertThat(state4.getTape().getCharactersAhead()).containsOnly();

        val state5 = result.get(4);
        assertThat(state5.getCurrentState()).isEqualTo(acceptState1);
        assertThat(state2.getTape().getCharactersBehind()).containsOnly('X');
        assertThat(state5.getTape().getCurrentPosition()).isEqualTo(Tape.EMPTY);
        assertThat(state5.getTape().getCharactersAhead()).containsOnly();
    }

    @Test
    public void acceptNonLinearAfterReadWholeInput() {
        final LinkedHashSet<TuringRule> rules = Sets.newLinkedHashSet(
                createRule(startState, 'A', Direction.FORWARD, middleState1, 'X'),
                createRule(startState, 'A', Direction.FORWARD, middleState2, 'X'),
                createRule(middleState1, 'B', Direction.FORWARD, middleState1, 'X'),
                createRule(middleState1, 'C', Direction.FORWARD, middleState1, 'X'),
                createRule(middleState2, 'B', Direction.FORWARD, middleState2, 'X'),
                createRule(middleState2, 'C', Direction.FORWARD, middleState2, 'X'),
                createRule(middleState1, Tape.EMPTY, Direction.STAY, acceptState1, Tape.EMPTY),
                createRule(middleState2, Tape.EMPTY, Direction.STAY, declineState1, Tape.EMPTY));
        this.turingMachine = createTuringMachine(Collections.singleton(acceptState1), Collections.singleton(declineState1), rules);

        final List<TuringState> result = new TuringCalculator(turingMachine).calculate("ABC");

        assertThat(result.size()).isEqualTo(5);

        val state1 = result.get(0);
        assertThat(state1.getCurrentState()).isEqualTo(startState);
        assertThat(state1.getTape().getCharactersBehind()).isEmpty();
        assertThat(state1.getTape().getCurrentPosition()).isEqualTo('A');
        assertThat(state1.getTape().getCharactersAhead()).containsOnly('B', 'C');

        val state2 = result.get(1);
        assertThat(state2.getCurrentState()).isEqualTo(middleState1);
        assertThat(state2.getTape().getCharactersBehind()).containsOnly('X');
        assertThat(state2.getTape().getCurrentPosition()).isEqualTo('B');
        assertThat(state2.getTape().getCharactersAhead()).containsOnly('C');

        val state3 = result.get(2);
        assertThat(state3.getCurrentState()).isEqualTo(middleState1);
        assertThat(state2.getTape().getCharactersBehind()).containsOnly('X');
        assertThat(state3.getTape().getCurrentPosition()).isEqualTo('C');
        assertThat(state3.getTape().getCharactersAhead()).isEmpty();

        val state4 = result.get(3);
        assertThat(state4.getCurrentState()).isEqualTo(middleState1);
        assertThat(state2.getTape().getCharactersBehind()).containsOnly('X');
        assertThat(state4.getTape().getCurrentPosition()).isEqualTo(Tape.EMPTY);
        assertThat(state4.getTape().getCharactersAhead()).containsOnly();

        val state5 = result.get(4);
        assertThat(state5.getCurrentState()).isEqualTo(acceptState1);
        assertThat(state2.getTape().getCharactersBehind()).containsOnly('X');
        assertThat(state5.getTape().getCurrentPosition()).isEqualTo(Tape.EMPTY);
        assertThat(state5.getTape().getCharactersAhead()).containsOnly();
    }

    @Test
    public void declineAfterMaximumStepsReached() {
        final LinkedHashSet<TuringRule> rules = Sets.newLinkedHashSet(
                createRule(startState, 'A', Direction.STAY, startState, 'A'));
        this.turingMachine = createTuringMachine(Sets.newHashSet(), Sets.newHashSet(), rules);

        final List<TuringState> result = new TuringCalculator(turingMachine).calculate("ABC");

        assertThat(result).isNull();
    }

    private TuringMachine createTuringMachine(final Set<MachineState> acceptStates, final Set<MachineState> declineStates, final Set<TuringRule> rules) {
        return TuringMachine.builder()
                .inputCharacters(characters)
                .tapeCharacters(characters)
                .states(states)
                .startState(startState)
                .acceptStates(acceptStates)
                .declineStates(declineStates)
                .rules(rules)
                .build();
    }

    private TuringRule createRule(final MachineState fromState, final Character readCharacter,
                                  final Direction direction, final MachineState toState, final Character writeCharacter){
        return TuringRule.builder()
                .fromState(fromState)
                .readCharacter(readCharacter)
                .direction(direction)
                .toState(toState)
                .writeCharacter(writeCharacter)
                .build();
    }
}
