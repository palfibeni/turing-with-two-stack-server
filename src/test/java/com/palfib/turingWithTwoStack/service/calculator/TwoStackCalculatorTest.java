package com.palfib.turingWithTwoStack.service.calculator;

import com.palfib.turingWithTwoStack.entity.Condition;
import com.palfib.turingWithTwoStack.entity.twoStack.TwoStackCondition;
import com.palfib.turingWithTwoStack.entity.twoStack.TwoStackMachine;
import com.palfib.turingWithTwoStack.entity.twoStack.TwoStackRule;
import lombok.val;
import org.assertj.core.util.Sets;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.List;

import static com.palfib.turingWithTwoStack.TestHelper.*;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Sets.newHashSet;

public class TwoStackCalculatorTest {

    private TwoStackMachine twoStackMachine;

    @Test
    public void acceptLinearAtStart() {
        val acceptStart = createState("START", true, true, false);
        this.twoStackMachine = createTwoStackMachine(newHashSet(), Sets.newLinkedHashSet(acceptStart));

        final List<TwoStackCondition> result = new TwoStackCalculator(twoStackMachine).calculate("ABC");

        assertThat(result.size()).isEqualTo(1);

        val condition = result.get(0);
        assertThat(condition.getCurrentState()).isEqualTo(acceptStart);
        assertThat(condition.getLeftStack()).isEmpty();
        assertThat(condition.getCurrentPosition()).isEqualTo('A');
        assertThat(condition.getRightStack()).containsExactly('C', 'B', 'A');
    }

    @Test
    public void declineLinearAtStart() {
        val declineStart = createState("START", true, false, true);
        this.twoStackMachine = createTwoStackMachine(emptySet(), Sets.newLinkedHashSet(declineStart));

        final List<TwoStackCondition> result = new TwoStackCalculator(twoStackMachine).calculate("ABC");

        assertThat(result).isNull();
    }

    @Test
    public void acceptLinearWithOneStep() {
        final LinkedHashSet<TwoStackRule> rules = Sets.newLinkedHashSet(
                createTwoStackRule(startState, 'A', acceptState1, "X", "", false));
        this.twoStackMachine = createTwoStackMachine(rules);

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
                createTwoStackRule(startState, 'A', declineState1, "X", "", false));
        this.twoStackMachine = createTwoStackMachine(rules);

        final List<TwoStackCondition> result = new TwoStackCalculator(twoStackMachine).calculate("ABC");

        assertThat(result).isNull();

    }

    @Test
    public void acceptLinearAfterReadWholeInput() {
        final LinkedHashSet<TwoStackRule> rules = Sets.newLinkedHashSet(
                createTwoStackRule(startState, 'A', middleState1, "X", "", false),
                createTwoStackRule(middleState1, 'B', middleState1, "X", "", false),
                createTwoStackRule(middleState1, 'C', middleState1, "X", "", false),
                createTwoStackRule(middleState1, Condition.EMPTY, acceptState1, "", Condition.EMPTY_AS_STRING, false));
        this.twoStackMachine = createTwoStackMachine(rules);

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
                createTwoStackRule(startState, 'A', middleState1, "X", "", false),
                createTwoStackRule(startState, 'A', middleState2, "X", "", false),
                createTwoStackRule(middleState1, 'B', middleState1, "X", "", false),
                createTwoStackRule(middleState1, 'C', middleState1, "X", "", false),
                createTwoStackRule(middleState2, 'B', middleState2, "X", "", false),
                createTwoStackRule(middleState2, 'C', middleState2, "X", "", false),
                createTwoStackRule(middleState1, Condition.EMPTY, acceptState1, "", Condition.EMPTY_AS_STRING, false),
                createTwoStackRule(middleState2, Condition.EMPTY, declineState1, "", Condition.EMPTY_AS_STRING, false)
        );
        this.twoStackMachine = createTwoStackMachine(rules);

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
                createTwoStackRule(startState, 'A', startState, "", "A", false));
        this.twoStackMachine = createTwoStackMachine(rules);

        final List<TwoStackCondition> result = new TwoStackCalculator(twoStackMachine).calculate("ABC");

        assertThat(result).isNull();
    }
}
