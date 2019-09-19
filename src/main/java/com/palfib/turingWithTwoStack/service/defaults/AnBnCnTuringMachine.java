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

    public static Long stateId = 1L;
    public static Long ruleId = 1L;
    public static final MachineState AT_FIRST_A = AnBnCnTuringMachine.createState("A_TO_X", true, false, false);
    public static final MachineState ACCEPT = AnBnCnTuringMachine.createState("ACCEPT", false, true, false);
    public static final MachineState DECLINE = AnBnCnTuringMachine.createState("DECLINE", false, false, true);
    public static final MachineState FORWARD_TO_B = AnBnCnTuringMachine.createState("FORWARD_TO_B");
    public static final MachineState BACK_TO_LAST_C = AnBnCnTuringMachine.createState("BACK_TO_LAST_C");
    public static final MachineState DELETE_C = AnBnCnTuringMachine.createState("DELETE_C");
    public static final MachineState CHECK_FOR_C = AnBnCnTuringMachine.createState("CHECK_FOR_C");
    public static final MachineState FORWARD_TO_C_END = AnBnCnTuringMachine.createState("FORWARD_TO_C_END");
    public static final MachineState BACK_TO_FIRST_A_FROM_C = AnBnCnTuringMachine.createState("BACK_TO_FIRST_A_FROM_C");
    public static final MachineState BACK_TO_FIRST_A_FROM_B = AnBnCnTuringMachine.createState("BACK_TO_FIRST_A_FROM_B");
    public static final MachineState BACK_TO_FIRST_A_FROM_A = AnBnCnTuringMachine.createState("BACK_TO_FIRST_A_FROM_A");
    public static final MachineState TEST_FOR_ANY_A_OR_B = AnBnCnTuringMachine.createState("TEST_FOR_ANY_A_OR_B");

    private static MachineState createState(final String name) {
        return AnBnCnTuringMachine.createState(name, false, false, false);
    }

    private static MachineState createState(
            final String name, final boolean isStart, final boolean isAccept, final boolean isDecline) {
        return MachineState.builder()
                .id(stateId++)
                .name(name)
                .start(isStart)
                .accept(isAccept)
                .decline(isDecline)
                .build();
    }

    public static TuringMachine createAnBnCnMachine() {
        return TuringMachine.builder()
                .inputCharacters(getCharacters())
                .states(getStates())
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
                .id(ruleId++)
                .fromState(fromState)
                .readCharacter(readCharacter)
                .direction(direction)
                .toState(toState)
                .writeCharacter(writeCharacter)
                .build();
    }
}
