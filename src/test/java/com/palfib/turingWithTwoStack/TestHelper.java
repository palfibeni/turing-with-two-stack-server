package com.palfib.turingWithTwoStack;

import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.enums.Direction;
import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import com.palfib.turingWithTwoStack.entity.turing.TuringRule;
import com.palfib.turingWithTwoStack.entity.twoStack.TwoStackCondition;
import com.palfib.turingWithTwoStack.entity.twoStack.TwoStackMachine;
import com.palfib.turingWithTwoStack.entity.twoStack.TwoStackRule;
import org.assertj.core.util.Sets;

import java.util.Set;

public class TestHelper {

    public static final Set<Character> characters = Sets.newLinkedHashSet('A', 'B', 'C');

    public static final MachineState startState = createState("START", true, false, false);
    public static final MachineState middleState1 = createState("MIDDLE1");
    public static final MachineState middleState2 = createState("MIDDLE2");
    public static final MachineState acceptState1 = createState("ACCEPT1", false, true, false);
    public static final MachineState acceptState2 = createState("ACCEPT2", false, true, false);
    public static final MachineState declineState1 = createState("DECLINE1", false, false, true);
    public static final MachineState declineState2 = createState("DECLINE2", false, false, true);

    public static final Set<MachineState> states = Sets.newLinkedHashSet(startState,
            middleState1, middleState2,
            acceptState1, acceptState2,
            declineState1, declineState2);

    public static MachineState createState(final String name) {
        return createState(name, false, false, false);
    }

    public static MachineState createState(
            final String name, final boolean isStart, final boolean isAccept, final boolean isDecline) {
        return MachineState.builder()
                .name(name)
                .start(isStart)
                .accept(isAccept)
                .decline(isDecline)
                .build();
    }

    public static TuringMachine createTuringMachine(final Set<TuringRule> rules) {
        return createTuringMachine(rules, states);
    }

    public static TuringMachine createTuringMachine(final Set<TuringRule> rules, final Set<MachineState> states) {
        return TuringMachine.builder()
                .inputCharacters(characters)
                .states(states)
                .rules(rules)
                .build();
    }

    public static TuringRule createTuringRule(final MachineState fromState, final Character readCharacter,
                                              final Direction direction, final MachineState toState, final Character writeCharacter) {
        return TuringRule.builder()
                .fromState(fromState)
                .readCharacter(readCharacter)
                .direction(direction)
                .toState(toState)
                .writeCharacter(writeCharacter)
                .build();
    }

    public static TwoStackMachine createTwoStackMachine(final Set<TwoStackRule> rules) {
        return createTwoStackMachine(rules, states);
    }

    public static TwoStackMachine createTwoStackMachine(final Set<TwoStackRule> rules, final Set<MachineState> states) {
        return TwoStackMachine.builder()
                .inputCharacters(characters)
                .states(states)
                .rules(rules)
                .build();
    }

    public static TwoStackRule createTwoStackRule(final MachineState fromState, final Character readLeft, final Character readRight, final Character readCharacter, final MachineState toState, final String writeLeft, final String writeRight, final boolean copyLeftToRight) {
        return TwoStackRule.builder()
                .fromState(fromState)
                .readLeft(readLeft)
                .readRight(readRight)
                .readCharacter(readCharacter)
                .toState(toState)
                .writeLeft(writeLeft)
                .writeRight(writeRight)
                .copyLeftToRight(copyLeftToRight)
                .build();
    }
}
