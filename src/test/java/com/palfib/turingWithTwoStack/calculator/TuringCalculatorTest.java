package com.palfib.turingWithTwoStack.calculator;

import com.palfib.turingWithTwoStack.entity.*;
import com.palfib.turingWithTwoStack.entity.enums.Direction;
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

    @Test
    public void acceptLinearAtStart() {
        this.turingMachine = createTuringMachine(Collections.singleton(startState), Sets.newHashSet(), Sets.newHashSet());

        final List<TuringCondition> result = new TuringCalculator(turingMachine).calculate("ABC");

        assertThat(result.size()).isEqualTo(1);

        val condition = result.get(0);
        assertThat(condition.getCurrentState()).isEqualTo(startState);
        assertThat(condition.getTuringTape().getCharactersBehind()).isEmpty();
        assertThat(condition.getCurrentPosition()).isEqualTo('A');
        assertThat(condition.getTuringTape().getCharactersAhead()).containsExactly('B', 'C');
    }

    @Test
    public void declineLinearAtStart() {
        this.turingMachine = createTuringMachine(Sets.newHashSet(), Collections.singleton(startState), Sets.newHashSet());

        final List<TuringCondition> result = new TuringCalculator(turingMachine).calculate("ABC");

        assertThat(result).isNull();
    }

    @Test
    public void acceptLinearWithOneStep() {
        final LinkedHashSet<TuringRule> rules = Sets.newLinkedHashSet(
                createRule(startState, 'A', Direction.STAY, acceptState1, 'X'));
        this.turingMachine = createTuringMachine(Collections.singleton(acceptState1), Sets.newHashSet(), rules);

        final List<TuringCondition> result = new TuringCalculator(turingMachine).calculate("ABC");

        assertThat(result.size()).isEqualTo(2);

        val condition1 = result.get(0);
        assertThat(condition1.getCurrentState()).isEqualTo(startState);
        assertThat(condition1.getTuringTape().getCharactersBehind()).isEmpty();
        assertThat(condition1.getCurrentPosition()).isEqualTo('A');
        assertThat(condition1.getTuringTape().getCharactersAhead()).containsExactly('B', 'C');

        val condition2 = result.get(1);
        assertThat(condition2.getCurrentState()).isEqualTo(acceptState1);
        assertThat(condition2.getTuringTape().getCharactersBehind()).isEmpty();
        assertThat(condition2.getCurrentPosition()).isEqualTo('X');
        assertThat(condition2.getTuringTape().getCharactersAhead()).containsExactly('B', 'C');
    }

    @Test
    public void declineLinearWithOneStep() {
        final LinkedHashSet<TuringRule> rules = Sets.newLinkedHashSet(
                createRule(startState, 'A', Direction.STAY, declineState1, 'X'));
        this.turingMachine = createTuringMachine(Sets.newHashSet(), Collections.singleton(declineState1), rules);

        final List<TuringCondition> result = new TuringCalculator(turingMachine).calculate("ABC");

        assertThat(result).isNull();

    }

    @Test
    public void acceptLinearAfterReadWholeInput() {
        final LinkedHashSet<TuringRule> rules = Sets.newLinkedHashSet(
                createRule(startState, 'A', Direction.FORWARD, middleState1, 'X'),
                createRule(middleState1, 'B', Direction.FORWARD, middleState1, 'X'),
                createRule(middleState1, 'C', Direction.FORWARD, middleState1, 'X'),
                createRule(middleState1, Condition.EMPTY, Direction.STAY, acceptState1, Condition.EMPTY));
        this.turingMachine = createTuringMachine(Collections.singleton(acceptState1), Sets.newHashSet(), rules);

        final List<TuringCondition> result = new TuringCalculator(turingMachine).calculate("ABC");

        assertThat(result.size()).isEqualTo(5);

        val condition1 = result.get(0);
        assertThat(condition1.getCurrentState()).isEqualTo(startState);
        assertThat(condition1.getTuringTape().getCharactersBehind()).isEmpty();
        assertThat(condition1.getCurrentPosition()).isEqualTo('A');
        assertThat(condition1.getTuringTape().getCharactersAhead()).containsExactly('B', 'C');

        val condition2 = result.get(1);
        assertThat(condition2.getCurrentState()).isEqualTo(middleState1);
        assertThat(condition2.getTuringTape().getCharactersBehind()).containsExactly('X');
        assertThat(condition2.getCurrentPosition()).isEqualTo('B');
        assertThat(condition2.getTuringTape().getCharactersAhead()).containsExactly('C');

        val condition3 = result.get(2);
        assertThat(condition3.getCurrentState()).isEqualTo(middleState1);
        assertThat(condition3.getTuringTape().getCharactersBehind()).containsExactly('X', 'X');
        assertThat(condition3.getCurrentPosition()).isEqualTo('C');
        assertThat(condition3.getTuringTape().getCharactersAhead()).isEmpty();

        val condition4 = result.get(3);
        assertThat(condition4.getCurrentState()).isEqualTo(middleState1);
        assertThat(condition4.getTuringTape().getCharactersBehind()).containsExactly('X', 'X', 'X');
        assertThat(condition4.getCurrentPosition()).isEqualTo(Condition.EMPTY);
        assertThat(condition4.getTuringTape().getCharactersAhead()).isEmpty();

        val condition5 = result.get(4);
        assertThat(condition5.getCurrentState()).isEqualTo(acceptState1);
        assertThat(condition5.getTuringTape().getCharactersBehind()).containsExactly('X', 'X', 'X');
        assertThat(condition5.getCurrentPosition()).isEqualTo(Condition.EMPTY);
        assertThat(condition5.getTuringTape().getCharactersAhead()).isEmpty();
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
                createRule(middleState1, Condition.EMPTY, Direction.STAY, acceptState1, Condition.EMPTY),
                createRule(middleState2, Condition.EMPTY, Direction.STAY, declineState1, Condition.EMPTY));
        this.turingMachine = createTuringMachine(Collections.singleton(acceptState1), Collections.singleton(declineState1), rules);

        final List<TuringCondition> result = new TuringCalculator(turingMachine).calculate("ABC");

        assertThat(result.size()).isEqualTo(5);

        val condition1 = result.get(0);
        assertThat(condition1.getCurrentState()).isEqualTo(startState);
        assertThat(condition1.getTuringTape().getCharactersBehind()).isEmpty();
        assertThat(condition1.getCurrentPosition()).isEqualTo('A');
        assertThat(condition1.getTuringTape().getCharactersAhead()).containsExactly('B', 'C');

        val condition2 = result.get(1);
        assertThat(condition2.getCurrentState()).isEqualTo(middleState1);
        assertThat(condition2.getTuringTape().getCharactersBehind()).containsExactly('X');
        assertThat(condition2.getCurrentPosition()).isEqualTo('B');
        assertThat(condition2.getTuringTape().getCharactersAhead()).containsExactly('C');

        val condition3 = result.get(2);
        assertThat(condition3.getCurrentState()).isEqualTo(middleState1);
        assertThat(condition3.getTuringTape().getCharactersBehind()).containsExactly('X', 'X');
        assertThat(condition3.getCurrentPosition()).isEqualTo('C');
        assertThat(condition3.getTuringTape().getCharactersAhead()).isEmpty();

        val condition4 = result.get(3);
        assertThat(condition4.getCurrentState()).isEqualTo(middleState1);
        assertThat(condition4.getTuringTape().getCharactersBehind()).containsExactly('X', 'X', 'X');
        assertThat(condition4.getCurrentPosition()).isEqualTo(Condition.EMPTY);
        assertThat(condition4.getTuringTape().getCharactersAhead()).isEmpty();

        val condition5 = result.get(4);
        assertThat(condition5.getCurrentState()).isEqualTo(acceptState1);
        assertThat(condition5.getTuringTape().getCharactersBehind()).containsExactly('X', 'X', 'X');
        assertThat(condition5.getCurrentPosition()).isEqualTo(Condition.EMPTY);
        assertThat(condition5.getTuringTape().getCharactersAhead()).isEmpty();
    }

    @Test
    public void declineAfterMaximumStepsReached() {
        final LinkedHashSet<TuringRule> rules = Sets.newLinkedHashSet(
                createRule(startState, 'A', Direction.STAY, startState, 'A'));
        this.turingMachine = createTuringMachine(Sets.newHashSet(), Sets.newHashSet(), rules);

        final List<TuringCondition> result = new TuringCalculator(turingMachine).calculate("ABC");

        assertThat(result).isNull();
    }

    private TuringMachine createTuringMachine(final Set<MachineState> acceptStates, final Set<MachineState> declineStates, final Set<TuringRule> rules) {
        return TuringMachine.builder()
                .inputCharacters(characters)
                .states(states)
                .startState(startState)
                .acceptStates(acceptStates)
                .declineStates(declineStates)
                .rules(rules)
                .build();
    }

    private TuringRule createRule(final MachineState fromState, final Character readCharacter,
                                  final Direction direction, final MachineState toState, final Character writeCharacter) {
        return TuringRule.builder()
                .fromState(fromState)
                .readCharacter(readCharacter)
                .direction(direction)
                .toState(toState)
                .writeCharacter(writeCharacter)
                .build();
    }
}
