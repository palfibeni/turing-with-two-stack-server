package com.palfib.turingWithTwoStack.entity.twoStack;

import com.palfib.turingWithTwoStack.entity.Condition;
import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.Rule;
import lombok.Getter;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static java.util.Collections.reverse;
import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * The right stack's first element holds the cursor's pointed character
 */
@Getter
public class TwoStackCondition extends Condition {

    public static final Character JOKER = '*';
    public static final String JOKER_AS_STRING = "*";
    public static final Character STACK_BOTTOM = '#';
    public static final String STACK_BOTTOM_AS_STRING = "#";

    private String input;

    private Stack<Character> leftStack = new Stack<>();

    private Stack<Character> rightStack = new Stack<>();

    public TwoStackCondition(final MachineState currentState, final String input) {
        super(currentState);
        this.input = input;
        this.leftStack.push(STACK_BOTTOM);
        this.rightStack.push(STACK_BOTTOM);
    }

    private void insertWordIntoStack(final String word, final Stack<Character> stack, final Character previous) {
        // If Stack Empty, and word has a StackBottom or Empty, place Bottom back.
        if (isEmpty(word)) {
            return;
        }
        if (stack.isEmpty() && (word.charAt(0) == EMPTY || word.charAt(0) == STACK_BOTTOM)) {
            stack.push(STACK_BOTTOM);
            insertWordIntoStack(word.substring(1), stack, null);
            return;
        }
        // If Stack has only bottom, and empty comes, dont do anything.
        if (stack.peek().equals(STACK_BOTTOM) && Condition.EMPTY_AS_STRING.equals(word)) {
            return;
        }
        // If Joker comes, place back the last read character.
        if (word.charAt(0) == JOKER && previous != null) {
            stack.push(previous);
            insertWordIntoStack(word.substring(1), stack, null);
            return;
        }
        word.chars()
                .mapToObj(ch -> (char) ch)
                .forEach(stack::push);
    }

    public TwoStackCondition(final MachineState currentState, final Stack<Character> leftStack,
                             final Stack<Character> rightStack, final String input) {
        super(currentState);
        this.leftStack.addAll(leftStack);
        this.rightStack.addAll(rightStack);
        this.input = input;
    }

    @Override
    public Character getCurrentPosition() {
        if (input.length() == 0) {
            return EMPTY;
        }
        return input.charAt(0);
    }

    @Override
    public boolean isValidRule(Rule rule) {
        if (!(rule instanceof TwoStackRule)) {
            throw new IllegalStateException("Only TwoStackRule can be used on TwoStackCondition!");
        }
        val twoStackRule = (TwoStackRule) rule;
        return twoStackRule.getFromState().equals(getCurrentState())
                && twoStackRule.getReadCharacter().equals(getCurrentPosition())
                && getTopCharacterPredicate(rightStack.peek(), twoStackRule.getReadRight())
                && getTopCharacterPredicate(leftStack.peek(), twoStackRule.getReadLeft());
    }

    private boolean getTopCharacterPredicate(Character topCharacter, Character ruleForCharacter) {
        return topCharacter.equals(ruleForCharacter)
                || (JOKER.equals(ruleForCharacter) && !STACK_BOTTOM.equals(topCharacter))
                || (EMPTY.equals(ruleForCharacter) && STACK_BOTTOM.equals(topCharacter));
    }

    @Override
    public void useRule(final Rule rule) {
        if (!(rule instanceof TwoStackRule)) {
            throw new IllegalStateException("Only TwoStackRule can be used on TwoStackCondition!");
        }
        val twoStackRule = (TwoStackRule) rule;
        // Read input
        readInput();
        val leftStack = this.leftStack.pop();
        val rightStack = this.rightStack.pop();

        // Write to all stack
        insertWordIntoStack(twoStackRule.getWriteLeft(), this.leftStack, leftStack);
        insertWordIntoStack(twoStackRule.getWriteRight(), this.rightStack, rightStack);

        //
        if (twoStackRule.isCopyLeftToRight()) {
            val topOfLeftStack = this.leftStack.peek().equals(STACK_BOTTOM)
                    ? Condition.EMPTY_AS_STRING
                    : this.leftStack.pop().toString();
            insertWordIntoStack(topOfLeftStack, this.rightStack, null);
        }
    }

    private void readInput() {
        if (input.length() > 0) {
            input = input.substring(1);
        }
    }

    @Override
    public List<Character> getCharactersAhead() {
        val charactersAhead = newArrayList(rightStack);
        reverse(charactersAhead);
        return charactersAhead;
    }

    @Override
    public List<Character> getCharactersBehind() {
        final ArrayList<Character> charactersBehind = newArrayList(this.leftStack);
        reverse(charactersBehind);
        return charactersBehind;
    }
}
