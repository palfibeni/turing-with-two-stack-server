package com.palfib.turingWithTwoStack.converter;

import com.palfib.turingWithTwoStack.calculator.TwoStackCalculator;
import com.palfib.turingWithTwoStack.defaults.AnBnCnTuringMachine;
import com.palfib.turingWithTwoStack.entity.Condition;
import com.palfib.turingWithTwoStack.entity.TwoStackMachine;
import lombok.val;
import org.junit.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class TwoStackMachineConverterTest {
    
    private final TwoStackMachine anBnCnTwoStackMachine = TwoStackMachineConverter.fromTuringMachine(AnBnCnTuringMachine.createAnBnCnMachine());

    @Test
    public void acceptForABC() {
        val result = new TwoStackCalculator(anBnCnTwoStackMachine).calculate("ABC");

        assertThat(result.size()).isEqualTo(9);

        val condition1 = result.get(0);
        assertThat(condition1.getCurrentState()).isEqualTo(AnBnCnTuringMachine.AT_FIRST_A);
        assertThat(condition1.getLeftStack()).isEmpty();
        assertThat(condition1.getCurrentPosition()).isEqualTo('A');
        assertThat(condition1.getRightStack()).containsExactly('C', 'B', 'A');

        val condition2 = result.get(1);
        assertThat(condition2.getCurrentState()).isEqualTo(AnBnCnTuringMachine.FORWARD_TO_B);
        assertThat(condition2.getLeftStack()).containsExactly('X');
        assertThat(condition2.getCurrentPosition()).isEqualTo('B');
        assertThat(condition2.getRightStack()).containsExactly('C', 'B');

        val condition3 = result.get(2);
        assertThat(condition3.getCurrentState()).isEqualTo(AnBnCnTuringMachine.FORWARD_TO_C_END);
        assertThat(condition3.getLeftStack()).containsExactly('X', 'Y');
        assertThat(condition3.getCurrentPosition()).isEqualTo('C');
        assertThat(condition3.getRightStack()).containsExactly('C');

        val condition4 = result.get(3);
        assertThat(condition4.getCurrentState()).isEqualTo(AnBnCnTuringMachine.FORWARD_TO_C_END);
        assertThat(condition4.getLeftStack()).containsExactly('X', 'Y', 'C');
        assertThat(condition4.getCurrentPosition()).isEqualTo(Condition.EMPTY);
        assertThat(condition4.getRightStack()).isEmpty();

        val condition5 = result.get(4);
        assertThat(condition5.getCurrentState()).isEqualTo(AnBnCnTuringMachine.DELETE_C);
        assertThat(condition5.getLeftStack()).containsExactly('X', 'Y');
        assertThat(condition5.getCurrentPosition()).isEqualTo('C');
        assertThat(condition5.getRightStack()).containsExactly('C');

        val condition6 = result.get(5);
        assertThat(condition6.getCurrentState()).isEqualTo(AnBnCnTuringMachine.CHECK_FOR_C);
        assertThat(condition6.getLeftStack()).containsExactly('X');
        assertThat(condition6.getCurrentPosition()).isEqualTo('Y');
        assertThat(condition6.getRightStack()).containsExactly('Y');

        val condition7 = result.get(6);
        assertThat(condition7.getCurrentState()).isEqualTo(AnBnCnTuringMachine.TEST_FOR_ANY_A_OR_B);
        assertThat(condition7.getLeftStack()).isEmpty();
        assertThat(condition7.getCurrentPosition()).isEqualTo('X');
        assertThat(condition7.getRightStack()).containsExactly('Y', 'X');

        val condition8 = result.get(7);
        assertThat(condition8.getCurrentState()).isEqualTo(AnBnCnTuringMachine.TEST_FOR_ANY_A_OR_B);
        assertThat(condition8.getLeftStack()).isEmpty();
        assertThat(condition8.getCurrentPosition()).isEqualTo(Condition.EMPTY);
        assertThat(condition8.getRightStack()).containsExactly('Y', 'X', Condition.EMPTY);

        val condition9 = result.get(8);
        assertThat(condition9.getCurrentState()).isEqualTo(AnBnCnTuringMachine.ACCEPT);
        assertThat(condition9.getLeftStack()).isEmpty();
        assertThat(condition9.getCurrentPosition()).isEqualTo(Condition.EMPTY);
        assertThat(condition9.getRightStack()).containsExactly('Y', 'X', Condition.EMPTY);
    }

    @Test
    public void acceptForAABBCC() {
        val result = new TwoStackCalculator(anBnCnTwoStackMachine).calculate("AABBCC");

        assertThat(result).isNotNull();
    }

    @Test
    public void declineForAAABBCC() {
        val result = new TwoStackCalculator(anBnCnTwoStackMachine).calculate("AAABBCC");

        assertThat(result).isNull();
    }

    @Test
    public void declineForAABBBCC() {
        val result = new TwoStackCalculator(anBnCnTwoStackMachine).calculate("AABBBCC");

        assertThat(result).isNull();
    }

    @Test
    public void declineForAABBCCC() {
        val result = new TwoStackCalculator(anBnCnTwoStackMachine).calculate("AABBCCC");

        assertThat(result).isNull();
    }


    @Test
    public void declineForAABBBCCC() {
        val result = new TwoStackCalculator(anBnCnTwoStackMachine).calculate("AABBBCCC");

        assertThat(result).isNull();
    }


    @Test
    public void declineForAAABBCCC() {
        val result = new TwoStackCalculator(anBnCnTwoStackMachine).calculate("AAABBCCC");

        assertThat(result).isNull();
    }

    @Test
    public void declineForAAABBBCC() {
        val result = new TwoStackCalculator(anBnCnTwoStackMachine).calculate("AAABBBCC");

        assertThat(result).isNull();
    }

    @Test
    public void acceptForAAABBBCCC() {
        val result = new TwoStackCalculator(anBnCnTwoStackMachine).calculate("AAABBBCCC");

        assertThat(result).isNotNull();
    }
}
