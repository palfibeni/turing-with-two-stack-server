package com.palfib.turingWithTwoStack.defaults;

import com.palfib.turingWithTwoStack.calculator.TuringCalculator;
import com.palfib.turingWithTwoStack.entity.Condition;
import com.palfib.turingWithTwoStack.entity.TuringCondition;
import com.palfib.turingWithTwoStack.entity.TuringMachine;
import lombok.val;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnBnCnTuringMachineTest {

    private final TuringMachine turingMachine = AnBnCnTuringMachine.createAnBnCnMachine();

    @Test
    public void acceptForABC() {
        final List<TuringCondition> result = new TuringCalculator(turingMachine).calculate("ABC");

        assertThat(result.size()).isEqualTo(9);

        val condition1 = result.get(0);
        assertThat(condition1.getCurrentState()).isEqualTo(AnBnCnTuringMachine.AT_FIRST_A);
        assertThat(condition1.getTuringTape().getCharactersBehind()).isEmpty();
        assertThat(condition1.getCurrentPosition()).isEqualTo('A');
        assertThat(condition1.getTuringTape().getCharactersAhead()).containsExactly('B', 'C');

        val condition2 = result.get(1);
        assertThat(condition2.getCurrentState()).isEqualTo(AnBnCnTuringMachine.FORWARD_TO_B);
        assertThat(condition2.getTuringTape().getCharactersBehind()).containsExactly('X');
        assertThat(condition2.getCurrentPosition()).isEqualTo('B');
        assertThat(condition2.getTuringTape().getCharactersAhead()).containsExactly('C');

        val condition3 = result.get(2);
        assertThat(condition3.getCurrentState()).isEqualTo(AnBnCnTuringMachine.FORWARD_TO_C_END);
        assertThat(condition3.getTuringTape().getCharactersBehind()).containsExactly('X', 'Y');
        assertThat(condition3.getCurrentPosition()).isEqualTo('C');
        assertThat(condition3.getTuringTape().getCharactersAhead()).isEmpty();

        val condition4 = result.get(3);
        assertThat(condition4.getCurrentState()).isEqualTo(AnBnCnTuringMachine.FORWARD_TO_C_END);
        assertThat(condition4.getTuringTape().getCharactersBehind()).containsExactly('X', 'Y', 'C');
        assertThat(condition4.getCurrentPosition()).isEqualTo(Condition.EMPTY);
        assertThat(condition4.getTuringTape().getCharactersAhead()).isEmpty();

        val condition5 = result.get(4);
        assertThat(condition5.getCurrentState()).isEqualTo(AnBnCnTuringMachine.DELETE_C);
        assertThat(condition5.getTuringTape().getCharactersBehind()).containsExactly('X', 'Y');
        assertThat(condition5.getCurrentPosition()).isEqualTo('C');
        assertThat(condition5.getTuringTape().getCharactersAhead()).isEmpty();

        val condition6 = result.get(5);
        assertThat(condition6.getCurrentState()).isEqualTo(AnBnCnTuringMachine.CHECK_FOR_C);
        assertThat(condition6.getTuringTape().getCharactersBehind()).containsExactly('X');
        assertThat(condition6.getCurrentPosition()).isEqualTo('Y');
        assertThat(condition6.getTuringTape().getCharactersAhead()).isEmpty();

        val condition7 = result.get(6);
        assertThat(condition7.getCurrentState()).isEqualTo(AnBnCnTuringMachine.TEST_FOR_ANY_A_OR_B);
        assertThat(condition7.getTuringTape().getCharactersBehind()).isEmpty();
        assertThat(condition7.getCurrentPosition()).isEqualTo('X');
        assertThat(condition7.getTuringTape().getCharactersAhead()).containsExactly('Y');

        val condition8 = result.get(7);
        assertThat(condition8.getCurrentState()).isEqualTo(AnBnCnTuringMachine.TEST_FOR_ANY_A_OR_B);
        assertThat(condition8.getTuringTape().getCharactersBehind()).isEmpty();
        assertThat(condition8.getCurrentPosition()).isEqualTo(Condition.EMPTY);
        assertThat(condition8.getTuringTape().getCharactersAhead()).containsExactly('X', 'Y');

        val condition9 = result.get(8);
        assertThat(condition9.getCurrentState()).isEqualTo(AnBnCnTuringMachine.ACCEPT);
        assertThat(condition9.getTuringTape().getCharactersBehind()).isEmpty();
        assertThat(condition9.getCurrentPosition()).isEqualTo(Condition.EMPTY);
        assertThat(condition9.getTuringTape().getCharactersAhead()).containsExactly('X', 'Y');
    }

    @Test
    public void acceptForAABBCC() {
        final List<TuringCondition> result = new TuringCalculator(turingMachine).calculate("AABBCC");

        assertThat(result).isNotNull();
    }

    @Test
    public void declineForAAABBCC() {
        final List<TuringCondition> result = new TuringCalculator(turingMachine).calculate("AAABBCC");

        assertThat(result).isNull();
    }

    @Test
    public void declineForAABBBCC() {
        final List<TuringCondition> result = new TuringCalculator(turingMachine).calculate("AABBBCC");

        assertThat(result).isNull();
    }

    @Test
    public void declineForAABBCCC() {
        final List<TuringCondition> result = new TuringCalculator(turingMachine).calculate("AABBCCC");

        assertThat(result).isNull();
    }


    @Test
    public void declineForAABBBCCC() {
        final List<TuringCondition> result = new TuringCalculator(turingMachine).calculate("AABBBCCC");

        assertThat(result).isNull();
    }


    @Test
    public void declineForAAABBCCC() {
        final List<TuringCondition> result = new TuringCalculator(turingMachine).calculate("AAABBCCC");

        assertThat(result).isNull();
    }

    @Test
    public void declineForAAABBBCC() {
        final List<TuringCondition> result = new TuringCalculator(turingMachine).calculate("AAABBBCC");

        assertThat(result).isNull();
    }

    @Test
    public void acceptForAAABBBCCC() {
        final List<TuringCondition> result = new TuringCalculator(turingMachine).calculate("AAABBBCCC");

        assertThat(result).isNotNull();
    }
}
