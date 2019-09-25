package com.palfib.turingWithTwoStack.service.converter;

import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.palfib.turingWithTwoStack.TestHelper.*;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TwoStackMachineConverterTest {

    @Mock
    private TwoStackRuleConverter twoStackRuleConverter;

    @InjectMocks
    private TwoStackMachineConverter twoStackMachineConverter;

    @Test
    public void convertEmptyTuringMachine() {
        val turingMachine = createTuringMachine(emptySet(), emptySet());

        val twoStackMachine = twoStackMachineConverter.fromTuringMachine(turingMachine);

        assertThat(twoStackMachine.getStates()).isEmpty();
        assertThat(twoStackMachine.getStartState()).isNull();
        assertThat(twoStackMachine.getAcceptStates()).isEmpty();
        assertThat(twoStackMachine.getDeclineStates()).isEmpty();
        assertThat(twoStackMachine.getRules()).isEmpty();
        assertEquals(twoStackMachine.getInputCharacters(), characters);
    }

    @Test
    public void convertTuringMachine() {
        val turingMachine = createTuringMachine(emptySet());

        val twoStackMachine = twoStackMachineConverter.fromTuringMachine(turingMachine);

        assertEquals(twoStackMachine.getStates(), states);
        assertThat(twoStackMachine.getStartState()).isEqualTo(startState);
        assertThat(twoStackMachine.getAcceptStates()).containsExactlyInAnyOrder(acceptState1, acceptState2);
        assertThat(twoStackMachine.getDeclineStates()).containsExactlyInAnyOrder(declineState1, declineState2);
        assertThat(twoStackMachine.getRules()).isEmpty();
        assertEquals(twoStackMachine.getInputCharacters(), characters);
    }
}
