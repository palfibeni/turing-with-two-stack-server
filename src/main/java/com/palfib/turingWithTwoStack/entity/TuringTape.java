package com.palfib.turingWithTwoStack.entity;

import com.palfib.turingWithTwoStack.entity.enums.Direction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayDeque;

import static java.util.stream.Collectors.toList;

/**
 * The TuringTape has a currentPosition priority character, which is the one, the cursor points at.
 */
@NoArgsConstructor
@ToString
@Getter
public class TuringTape {

    private final ArrayDeque<Character> charactersAhead = new ArrayDeque<>();

    private final ArrayDeque<Character> charactersBehind = new ArrayDeque<>();

    @Getter
    private Character currentPosition;

    TuringTape(final String input) {
        this.currentPosition = input.charAt(0);
        this.charactersAhead.addAll(input.substring(1).chars()
                .mapToObj(ch -> (char) ch)
                .collect(toList()));
    }

    TuringTape(final TuringTape turingTape) {
        this.currentPosition = turingTape.currentPosition;
        this.charactersAhead.addAll(turingTape.charactersAhead);
        this.charactersBehind.addAll(turingTape.charactersBehind);
    }

    Character moveCursorForward(final Character characterToWrite) {
        if(!charactersBehind.isEmpty() || !Condition.EMPTY.equals(characterToWrite)) {
            this.charactersBehind.addLast(characterToWrite);
        }
        this.currentPosition = getFirstFromAhead();
        return currentPosition;
    }

    Character moveCursorBackward(final Character characterToWrite) {
        if(!charactersAhead.isEmpty() || !Condition.EMPTY.equals(characterToWrite)) {
            this.charactersAhead.addFirst(characterToWrite);
        }
        this.currentPosition = getLastFromBehind();
        return currentPosition;
    }

    Character stayWithCursor(final Character characterToWrite) {
        this.currentPosition = characterToWrite;
        return currentPosition;
    }

    private Character getFirstFromAhead() {
        if (charactersAhead.isEmpty()) {
            return Condition.EMPTY;
        }
        return charactersAhead.removeFirst();
    }

    private Character getLastFromBehind() {
        if (charactersBehind.isEmpty()) {
            return Condition.EMPTY;
        }
        return charactersBehind.removeLast();
    }
}
