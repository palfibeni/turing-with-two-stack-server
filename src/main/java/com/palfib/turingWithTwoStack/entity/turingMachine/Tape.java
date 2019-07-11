package com.palfib.turingWithTwoStack.entity.turingMachine;

import com.palfib.turingWithTwoStack.entity.enums.Direction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayDeque;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor
@ToString
@Getter
public class Tape {

    public static final Character EMPTY = '_';

    private final ArrayDeque<Character> charactersAhead = new ArrayDeque<>();

    private final ArrayDeque<Character> charactersBehind = new ArrayDeque<>();

    @Getter
    private Character currentPosition;

    public Tape(final String input) {
        this.currentPosition = input.charAt(0);
        this.charactersAhead.addAll(input.substring(1).chars()
                .mapToObj(ch -> (char) ch)
                .collect(toList()));
    }

    public Tape(final Tape tape) {
        this.currentPosition = tape.currentPosition;
        this.charactersAhead.addAll(tape.charactersAhead);
        this.charactersBehind.addAll(tape.charactersBehind);
    }

    public void useRule(final Direction direction, final Character writeCharacter) {
        switch (direction){
            case FORWARD:
                this.moveCursorForward(writeCharacter);
                break;
            case BACKWARD:
                this.moveCursorBackward(writeCharacter);
                break;
            case STAY:
                this.stayWithCursor(writeCharacter);
                break;
             default:
                 throw new IllegalStateException("Not known direction: " + direction);
        }
    }

    private Character moveCursorForward(final Character characterToWrite) {
        this.charactersBehind.addLast(characterToWrite);
        this.currentPosition = getFirstFromAhead();
        return currentPosition;
    }

    private Character moveCursorBackward(final Character characterToWrite) {
        this.charactersAhead.addFirst(characterToWrite);
        this.currentPosition = getLastFromBehind();
        return currentPosition;
    }

    private Character stayWithCursor(final Character characterToWrite) {
        this.currentPosition = characterToWrite;
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
