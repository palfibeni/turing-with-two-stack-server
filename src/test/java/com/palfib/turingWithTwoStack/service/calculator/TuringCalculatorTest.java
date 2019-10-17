package com.palfib.turingWithTwoStack.service.calculator;

import com.palfib.turingWithTwoStack.entity.Condition;
import com.palfib.turingWithTwoStack.entity.enums.Direction;
import com.palfib.turingWithTwoStack.entity.turing.TuringCondition;
import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import com.palfib.turingWithTwoStack.entity.turing.TuringRule;
import lombok.val;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.List;

import static com.palfib.turingWithTwoStack.TestHelper.*;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;

public class TuringCalculatorTest {

    private TuringMachine turingMachine;

    @Test
    public void acceptLinearAtStart() {
        val acceptStart = createState("START", true, true, false);
        this.turingMachine = createTuringMachine(emptySet(), Sets.newLinkedHashSet(acceptStart));

        final List<TuringCondition> result = new TuringCalculator(turingMachine).calculate("ABC");

        assertThat(result.size()).isEqualTo(1);
        val condition = result.get(0);
        assertThat(condition.getCurrentState()).isEqualTo(acceptStart);
        assertThat(condition.getTuringTape().getCharactersBehind()).isEmpty();
        assertThat(condition.getCurrentPosition()).isEqualTo('A');
        assertThat(condition.getTuringTape().getCharactersAhead()).containsExactly('B', 'C');
    }

    @Test
    public void declineLinearAtStart() {
        val declineStart = createState("START", true, false, true);
        this.turingMachine = createTuringMachine(Sets.newHashSet(), Sets.newLinkedHashSet(declineStart));

        final List<TuringCondition> result = new TuringCalculator(turingMachine).calculate("ABC");

        assertThat(result).isNull();
    }

    @Test
    public void acceptLinearWithOneStep() {
        final LinkedHashSet<TuringRule> rules = Sets.newLinkedHashSet(
                createTuringRule(startState, 'A', Direction.STAY, acceptState1, 'X'));
        this.turingMachine = createTuringMachine(rules);

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
                createTuringRule(startState, 'A', Direction.STAY, declineState1, 'X'));
        this.turingMachine = createTuringMachine(rules);

        final List<TuringCondition> result = new TuringCalculator(turingMachine).calculate("ABC");

        assertThat(result).isNull();

    }

    @Test
    public void acceptLinearAfterReadWholeInput() {
        final LinkedHashSet<TuringRule> rules = Sets.newLinkedHashSet(
                createTuringRule(startState, 'A', Direction.FORWARD, middleState1, 'X'),
                createTuringRule(middleState1, 'B', Direction.FORWARD, middleState1, 'X'),
                createTuringRule(middleState1, 'C', Direction.FORWARD, middleState1, 'X'),
                createTuringRule(middleState1, Condition.EMPTY, Direction.STAY, acceptState1, Condition.EMPTY));
        this.turingMachine = createTuringMachine(rules);

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
                createTuringRule(startState, 'A', Direction.FORWARD, middleState1, 'X'),
                createTuringRule(startState, 'A', Direction.FORWARD, middleState2, 'X'),
                createTuringRule(middleState1, 'B', Direction.FORWARD, middleState1, 'X'),
                createTuringRule(middleState1, 'C', Direction.FORWARD, middleState1, 'X'),
                createTuringRule(middleState2, 'B', Direction.FORWARD, middleState2, 'X'),
                createTuringRule(middleState2, 'C', Direction.FORWARD, middleState2, 'X'),
                createTuringRule(middleState1, Condition.EMPTY, Direction.STAY, acceptState1, Condition.EMPTY),
                createTuringRule(middleState2, Condition.EMPTY, Direction.STAY, declineState1, Condition.EMPTY));
        this.turingMachine = createTuringMachine(rules);

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
                createTuringRule(startState, 'A', Direction.STAY, startState, 'A'));
        this.turingMachine = createTuringMachine(rules);

        final List<TuringCondition> result = new TuringCalculator(turingMachine).calculate("ABC");

        assertThat(result).isNull();
    }
}
