package com.palfib.turingWithTwoStack.calculator;

import com.palfib.turingWithTwoStack.entity.*;
import lombok.val;
import org.assertj.core.util.Sets;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TwoStackCalculatorTest {

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

    private TwoStackMachine twoStackMachine;

    @Test
    public void acceptLinearAtStart() {
        this.twoStackMachine = createTwoStackMachine(Collections.singleton(startState), Sets.newHashSet(), Sets.newHashSet());

        final List<TwoStackCondition> result = new TwoStackCalculator(twoStackMachine).calculate("ABC");

        assertThat(result.size()).isEqualTo(1);

        val condition = result.get(0);
        assertThat(condition.getCurrentState()).isEqualTo(startState);
        assertThat(condition.getLeftStack()).isEmpty();
        assertThat(condition.getCurrentPosition()).isEqualTo('A');
        assertThat(condition.getRightStack()).containsExactly('C', 'B', 'A');
    }

    @Test
    public void declineLinearAtStart() {
        this.twoStackMachine = createTwoStackMachine(Sets.newHashSet(), Collections.singleton(startState), Sets.newHashSet());

        final List<TwoStackCondition> result = new TwoStackCalculator(twoStackMachine).calculate("ABC");

        assertThat(result).isNull();
    }

    @Test
    public void acceptLinearWithOneStep() {
        final LinkedHashSet<TwoStackRule> rules = Sets.newLinkedHashSet(
                createRule(startState, 'A', acceptState1, "X", "", false));
        this.twoStackMachine = createTwoStackMachine(Collections.singleton(acceptState1), Sets.newHashSet(), rules);

        final List<TwoStackCondition> result = new TwoStackCalculator(twoStackMachine).calculate("ABC");

        assertThat(result.size()).isEqualTo(2);

        val condition1 = result.get(0);
        assertThat(condition1.getCurrentState()).isEqualTo(startState);
        assertThat(condition1.getLeftStack()).isEmpty();
        assertThat(condition1.getCurrentPosition()).isEqualTo('A');
        assertThat(condition1.getRightStack()).containsExactly('C', 'B', 'A');

        val condition2 = result.get(1);
        assertThat(condition2.getCurrentState()).isEqualTo(acceptState1);
        assertThat(condition2.getLeftStack()).containsExactly('X');
        assertThat(condition2.getCurrentPosition()).isEqualTo('B');
        assertThat(condition2.getRightStack()).containsExactly('C', 'B');
    }

    @Test
    public void declineLinearWithOneStep() {
        final LinkedHashSet<TwoStackRule> rules = Sets.newLinkedHashSet(
                createRule(startState, 'A', declineState1, "X", "", false));
        this.twoStackMachine = createTwoStackMachine(Sets.newHashSet(), Collections.singleton(declineState1), rules);

        final List<TwoStackCondition> result = new TwoStackCalculator(twoStackMachine).calculate("ABC");

        assertThat(result).isNull();

    }

    @Test
    public void acceptLinearAfterReadWholeInput() {
        final LinkedHashSet<TwoStackRule> rules = Sets.newLinkedHashSet(
                createRule(startState, 'A', middleState1, "X", "", false),
                createRule(middleState1, 'B', middleState1, "X", "", false),
                createRule(middleState1, 'C', middleState1, "X", "", false),
                createRule(middleState1, Condition.EMPTY, acceptState1, "", Condition.EMPTY_AS_STRING, false));
        this.twoStackMachine = createTwoStackMachine(Collections.singleton(acceptState1), Sets.newHashSet(), rules);

        final List<TwoStackCondition> result = new TwoStackCalculator(twoStackMachine).calculate("ABC");

        assertThat(result.size()).isEqualTo(5);

        val condition1 = result.get(0);
        assertThat(condition1.getCurrentState()).isEqualTo(startState);
        assertThat(condition1.getLeftStack()).isEmpty();
        assertThat(condition1.getCurrentPosition()).isEqualTo('A');
        assertThat(condition1.getRightStack()).containsExactly('C', 'B', 'A');

        val condition2 = result.get(1);
        assertThat(condition2.getCurrentState()).isEqualTo(middleState1);
        assertThat(condition2.getLeftStack()).containsExactly('X');
        assertThat(condition2.getCurrentPosition()).isEqualTo('B');
        assertThat(condition2.getRightStack()).containsExactly('C', 'B');

        val condition3 = result.get(2);
        assertThat(condition3.getCurrentState()).isEqualTo(middleState1);
        assertThat(condition3.getLeftStack()).containsExactly('X', 'X');
        assertThat(condition3.getCurrentPosition()).isEqualTo('C');
        assertThat(condition3.getRightStack()).containsExactly('C');

        val condition4 = result.get(3);
        assertThat(condition4.getCurrentState()).isEqualTo(middleState1);
        assertThat(condition4.getLeftStack()).containsExactly('X', 'X', 'X');
        assertThat(condition4.getCurrentPosition()).isEqualTo(Condition.EMPTY);
        assertThat(condition4.getRightStack()).isEmpty();

        val condition5 = result.get(4);
        assertThat(condition5.getCurrentState()).isEqualTo(acceptState1);
        assertThat(condition5.getLeftStack()).containsExactly('X', 'X', 'X');
        assertThat(condition5.getCurrentPosition()).isEqualTo(Condition.EMPTY);
        assertThat(condition5.getRightStack()).isEmpty();
    }

    @Test
    public void acceptNonLinearAfterReadWholeInput() {
        final LinkedHashSet<TwoStackRule> rules = Sets.newLinkedHashSet(
                createRule(startState, 'A', middleState1, "X", "", false),
                createRule(startState, 'A', middleState2, "X", "", false),
                createRule(middleState1, 'B', middleState1, "X", "", false),
                createRule(middleState1, 'C', middleState1, "X", "", false),
                createRule(middleState2, 'B', middleState2, "X", "", false),
                createRule(middleState2, 'C', middleState2, "X", "", false),
                createRule(middleState1, Condition.EMPTY, acceptState1, "", Condition.EMPTY_AS_STRING, false),
                createRule(middleState2, Condition.EMPTY, declineState1, "", Condition.EMPTY_AS_STRING, false)
        );
        this.twoStackMachine = createTwoStackMachine(Collections.singleton(acceptState1), Collections.singleton(declineState1), rules);

        final List<TwoStackCondition> result = new TwoStackCalculator(twoStackMachine).calculate("ABC");

        assertThat(result.size()).isEqualTo(5);

        val condition1 = result.get(0);
        assertThat(condition1.getCurrentState()).isEqualTo(startState);
        assertThat(condition1.getLeftStack()).isEmpty();
        assertThat(condition1.getCurrentPosition()).isEqualTo('A');
        assertThat(condition1.getRightStack()).containsExactly('C', 'B', 'A');

        val condition2 = result.get(1);
        assertThat(condition2.getCurrentState()).isEqualTo(middleState1);
        assertThat(condition2.getLeftStack()).containsExactly('X');
        assertThat(condition2.getCurrentPosition()).isEqualTo('B');
        assertThat(condition2.getRightStack()).containsExactly('C', 'B');

        val condition3 = result.get(2);
        assertThat(condition3.getCurrentState()).isEqualTo(middleState1);
        assertThat(condition3.getLeftStack()).containsExactly('X', 'X');
        assertThat(condition3.getCurrentPosition()).isEqualTo('C');
        assertThat(condition3.getRightStack()).containsExactly('C');

        val condition4 = result.get(3);
        assertThat(condition4.getCurrentState()).isEqualTo(middleState1);
        assertThat(condition4.getLeftStack()).containsExactly('X', 'X', 'X');
        assertThat(condition4.getCurrentPosition()).isEqualTo(Condition.EMPTY);
        assertThat(condition4.getRightStack()).isEmpty();

        val condition5 = result.get(4);
        assertThat(condition5.getCurrentState()).isEqualTo(acceptState1);
        assertThat(condition5.getLeftStack()).containsExactly('X', 'X', 'X');
        assertThat(condition5.getCurrentPosition()).isEqualTo(Condition.EMPTY);
        assertThat(condition5.getRightStack()).isEmpty();
    }

    @Test
    public void declineAfterMaximumStepsReached() {
        final LinkedHashSet<TwoStackRule> rules = Sets.newLinkedHashSet(
                createRule(startState, 'A', startState, "", "A", false));
        this.twoStackMachine = createTwoStackMachine(Sets.newHashSet(), Sets.newHashSet(), rules);

        final List<TwoStackCondition> result = new TwoStackCalculator(twoStackMachine).calculate("ABC");

        assertThat(result).isNull();
    }

    private TwoStackMachine createTwoStackMachine(final Set<MachineState> acceptStates, final Set<MachineState> declineStates, final Set<TwoStackRule> rules) {
        return TwoStackMachine.builder()
                .inputCharacters(characters)
                .states(states)
                .startState(startState)
                .acceptStates(acceptStates)
                .declineStates(declineStates)
                .rules(rules)
                .build();
    }

    private TwoStackRule createRule(final MachineState fromState, final Character readCharacter, final MachineState toState, final String writeLeft, final String writeRight, final boolean copyLeftToRight) {
        return TwoStackRule.builder()
                .fromState(fromState)
                .readCharacter(readCharacter)
                .toState(toState)
                .writeLeft(writeLeft)
                .writeRight(writeRight)
                .copyLeftToRight(copyLeftToRight)
                .build();
    }
}
