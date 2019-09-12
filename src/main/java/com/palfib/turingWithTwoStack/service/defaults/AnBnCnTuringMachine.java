package com.palfib.turingWithTwoStack.service.defaults;

import com.palfib.turingWithTwoStack.entity.Condition;
import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import com.palfib.turingWithTwoStack.entity.turing.TuringRule;
import com.palfib.turingWithTwoStack.entity.enums.Direction;
import lombok.val;

import java.util.HashSet;
import java.util.Set;

public class AnBnCnTuringMachine {

    public static final MachineState AT_FIRST_A = new MachineState("A_TO_X");
    public static final MachineState ACCEPT = new MachineState("ACCEPT");
    public static final MachineState DECLINE = new MachineState("DECLINE");
    public static final MachineState FORWARD_TO_B = new MachineState("FORWARD_TO_B");
    public static final MachineState BACK_TO_LAST_C = new MachineState("BACK_TO_LAST_C");
    public static final MachineState DELETE_C = new MachineState("DELETE_C");
    public static final MachineState CHECK_FOR_C = new MachineState("CHECK_FOR_C");
    public static final MachineState FORWARD_TO_C_END = new MachineState("FORWARD_TO_C_END");
    public static final MachineState BACK_TO_FIRST_A_FROM_C = new MachineState("BACK_TO_FIRST_A_FROM_C");
    public static final MachineState BACK_TO_FIRST_A_FROM_B = new MachineState("BACK_TO_FIRST_A_FROM_B");
    public static final MachineState BACK_TO_FIRST_A_FROM_A = new MachineState("BACK_TO_FIRST_A_FROM_A");
    public static final MachineState TEST_FOR_ANY_A_OR_B = new MachineState("TEST_FOR_ANY_A_OR_B");

    public static TuringMachine createAnBnCnMachine() {
        val acceptStates = new HashSet<MachineState>();
        acceptStates.add(ACCEPT);
        val declineStates = new HashSet<MachineState>();
        declineStates.add(DECLINE);
        return TuringMachine.builder()
                .inputCharacters(getCharacters())
                .states(getStates())
                .startState(AT_FIRST_A)
                .acceptStates(acceptStates)
                .declineStates(declineStates)
                .rules(getRules())
                .build();
    }

    private static Set<Character> getCharacters() {
        val characters = new HashSet<Character>();
        characters.add('A');
        characters.add('B');
        characters.add('C');
        characters.add('X');
        characters.add('Y');
        return characters;
    }

    private static Set<MachineState> getStates() {
        val states = new HashSet<MachineState>();
        states.add(AT_FIRST_A);
        states.add(FORWARD_TO_B);
        states.add(FORWARD_TO_C_END);
        states.add(BACK_TO_LAST_C); // If there is NO C, check if there is B, or A, if so, decline, if no, accept (TEST_FOR_ANY_A_OR_B)
        states.add(DELETE_C);
        states.add(CHECK_FOR_C);
        states.add(BACK_TO_FIRST_A_FROM_C);
        states.add(BACK_TO_FIRST_A_FROM_B);
        states.add(BACK_TO_FIRST_A_FROM_A);
        states.add(TEST_FOR_ANY_A_OR_B);
        states.add(ACCEPT);
        states.add(DECLINE);
        return states;
    }

    private static Set<TuringRule> getRules() {
        val rules = new HashSet<TuringRule>();
        //Iterate forward reducing number of A, B, and C by one.
        rules.add(createRule(AT_FIRST_A, 'A', Direction.FORWARD, FORWARD_TO_B, 'X'));
        rules.add(createRule(FORWARD_TO_B, 'A', Direction.FORWARD, FORWARD_TO_B, 'A'));
        rules.add(createRule(FORWARD_TO_B, 'Y', Direction.FORWARD, FORWARD_TO_B, 'Y'));
        rules.add(createRule(FORWARD_TO_B, 'B', Direction.FORWARD, FORWARD_TO_C_END, 'Y'));
        rules.add(createRule(FORWARD_TO_C_END, 'B', Direction.FORWARD, FORWARD_TO_C_END, 'B'));
        rules.add(createRule(FORWARD_TO_C_END, 'Y', Direction.FORWARD, FORWARD_TO_C_END, 'Y'));
        rules.add(createRule(FORWARD_TO_C_END, 'C', Direction.FORWARD, FORWARD_TO_C_END, 'C'));
        rules.add(createRule(FORWARD_TO_C_END, Condition.EMPTY, Direction.BACKWARD, DELETE_C, Condition.EMPTY));
        rules.add(createRule(DELETE_C, 'C', Direction.BACKWARD, CHECK_FOR_C, Condition.EMPTY));

        //Iterate backward if there is remaining C
        rules.add(createRule(CHECK_FOR_C, 'C', Direction.BACKWARD, BACK_TO_FIRST_A_FROM_C, 'C'));
        rules.add(createRule(BACK_TO_FIRST_A_FROM_C, 'C', Direction.BACKWARD, BACK_TO_FIRST_A_FROM_C, 'C'));
        rules.add(createRule(BACK_TO_FIRST_A_FROM_C, 'B', Direction.BACKWARD, BACK_TO_FIRST_A_FROM_B, 'B'));
        rules.add(createRule(BACK_TO_FIRST_A_FROM_B, 'B', Direction.BACKWARD, BACK_TO_FIRST_A_FROM_B, 'B'));
        rules.add(createRule(BACK_TO_FIRST_A_FROM_B, 'Y', Direction.BACKWARD, BACK_TO_FIRST_A_FROM_B, 'Y'));
        rules.add(createRule(BACK_TO_FIRST_A_FROM_B, 'A', Direction.BACKWARD, BACK_TO_FIRST_A_FROM_A, 'A'));
        rules.add(createRule(BACK_TO_FIRST_A_FROM_A, 'A', Direction.BACKWARD, BACK_TO_FIRST_A_FROM_A, 'A'));
        rules.add(createRule(BACK_TO_FIRST_A_FROM_A, 'X', Direction.FORWARD, AT_FIRST_A, 'X'));

        // Going backwards and A or B is not found, while there is still C left
        rules.add(createRule(BACK_TO_FIRST_A_FROM_C, 'Y', Direction.STAY, DECLINE, 'Y'));
        rules.add(createRule(BACK_TO_FIRST_A_FROM_B, 'X', Direction.STAY, DECLINE, 'X'));

        //Iterate backward if there is no remaining C, checking for A or B, to decline, or accept, if found non.
        rules.add(createRule(CHECK_FOR_C, 'B', Direction.STAY, DECLINE, 'B'));
        rules.add(createRule(CHECK_FOR_C, 'Y', Direction.BACKWARD, TEST_FOR_ANY_A_OR_B, 'Y'));
        rules.add(createRule(TEST_FOR_ANY_A_OR_B, 'Y', Direction.BACKWARD, TEST_FOR_ANY_A_OR_B, 'Y'));
        rules.add(createRule(TEST_FOR_ANY_A_OR_B, 'A', Direction.STAY, DECLINE, 'A'));
        rules.add(createRule(TEST_FOR_ANY_A_OR_B, 'X', Direction.BACKWARD, TEST_FOR_ANY_A_OR_B, 'X'));
        rules.add(createRule(TEST_FOR_ANY_A_OR_B, Condition.EMPTY, Direction.STAY, ACCEPT, Condition.EMPTY));


        return rules;
    }

    private static TuringRule createRule(final MachineState fromState, final Character readCharacter,
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
