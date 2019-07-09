package com.palfib.turingWithTwoStack.data.turingMachine;

import lombok.val;

import java.util.ArrayDeque;
import java.util.stream.Collectors;

public class Tape {

    public static Character EMPTY = '_';

    private ArrayDeque<Character> charactersAhead;

    private ArrayDeque<Character> charactersBehind;

    private Character currentPosition;

    public Tape(String input) {
        currentPosition = input.charAt(0);
        charactersAhead = input.chars()
                .mapToObj(ch -> (char) ch)
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    public Character moveCursorForward() {
        val next = charactersAhead.removeFirst();
        charactersBehind.addLast(currentPosition);
        currentPosition = next;
        return currentPosition;
    }

    public Character moveCursorBackward() {
        val next = charactersBehind.removeLast();
        charactersAhead.addFirst(currentPosition);
        currentPosition = next;
        return currentPosition;
    }

    private Character getFirstFromAhead() {
        if (charactersAhead.isEmpty()) {
            return EMPTY;
        }
        return charactersAhead.removeFirst();
    }

    private Character getLastFromBehind() {
        if (charactersBehind.isEmpty()) {
            return EMPTY;
        }
        return charactersBehind.removeLast();
    }
}
