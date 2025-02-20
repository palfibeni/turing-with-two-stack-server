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
    public void convertTuringMachine() {
        val turingMachine = createTuringMachine(emptySet());

        val twoStackMachine = twoStackMachineConverter.fromTuringMachine(turingMachine);

        assertThat(twoStackMachine.getStates()).containsAll(states);
        assertEquals(twoStackMachine.getStates().size(), states.size() + 2);
        assertThat(twoStackMachine.getAcceptStates()).containsExactlyInAnyOrder(acceptState1, acceptState2);
        assertThat(twoStackMachine.getDeclineStates()).containsExactlyInAnyOrder(declineState1, declineState2);
        assertThat(twoStackMachine.getRules()).isEmpty();
        assertEquals(twoStackMachine.getInputCharacters(), characters);
    }
}
