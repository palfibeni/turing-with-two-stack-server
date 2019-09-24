package com.palfib.turingWithTwoStack.service.converter;

import com.palfib.turingWithTwoStack.entity.Condition;
import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.enums.Direction;
import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import com.palfib.turingWithTwoStack.entity.turing.TuringRule;
import com.palfib.turingWithTwoStack.entity.twoStack.TwoStackMachine;
import com.palfib.turingWithTwoStack.service.calculator.TwoStackCalculator;
import com.palfib.turingWithTwoStack.service.defaults.AnBnCnTuringMachine;
import lombok.val;
import org.assertj.core.util.Sets;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;

import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TwoStackMachineConverterTest {

    private final Set<Character> characters = Sets.newLinkedHashSet('A', 'B', 'C');

    private final MachineState startState = createState("START", true, false, false);
    private final MachineState middleState1 = createState("MIDDLE1");
    private final MachineState middleState2 = createState("MIDDLE2");
    private final MachineState acceptState1 = createState("ACCEPT1", false, true, false);
    private final MachineState acceptState2 = createState("ACCEPT2", false, true, false);
    private final MachineState declineState1 = createState("DECLINE1", false, false, true);
    private final MachineState declineState2 = createState("DECLINE2", false, false, true);

    private final Set<MachineState> states = Sets.newLinkedHashSet(startState,
            middleState1, middleState2,
            acceptState1, acceptState2,
            declineState1, declineState2);
    @Mock
    private TwoStackRuleConverter twoStackRuleConverter;

    @InjectMocks
    private TwoStackMachineConverter twoStackMachineConverter;

    @Test
    public void convertEmptyTuringMachine() {
        val turingMachine = createTuringMachine(emptySet(), emptySet());

        val twoStackMachine = twoStackMachineConverter.fromTuringMachine(turingMachine);

        assertThat(twoStackMachine.getStartState()).isNull();
        assertThat(twoStackMachine.getAcceptStates()).isEmpty();
        assertThat(twoStackMachine.getDeclineStates()).isEmpty();
        assertThat(twoStackMachine.getRules()).isEmpty();
        assertThat(twoStackMachine.getInputCharacters()).isNotEmpty();
    }

    private MachineState createState(final String name) {
        return createState(name, false, false, false);
    }

    private MachineState createState(
            final String name, final boolean isStart, final boolean isAccept, final boolean isDecline) {
        return MachineState.builder()
                .name(name)
                .start(isStart)
                .accept(isAccept)
                .decline(isDecline)
                .build();
    }

    private TuringMachine createTuringMachine(final Set<TuringRule> rules) {
        return createTuringMachine(rules, states);
    }

    private TuringMachine createTuringMachine(final Set<TuringRule> rules, final Set<MachineState> states) {
        return TuringMachine.builder()
                .inputCharacters(characters)
                .states(states)
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
