package com.palfib.turingWithTwoStack.entity.twoStack;

import com.palfib.turingWithTwoStack.entity.Condition;
import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.Rule;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import static java.util.Collections.reverse;
import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;

/**
 * The right stack's first element holds the cursor's pointed character
 */
@Getter
public class TwoStackCondition extends Condition {

    private Stack<Character> leftStack = new Stack<>();

    private Stack<Character> rightStack = new Stack<>();



    public TwoStackCondition(final MachineState currentState, final String input) {
        super(currentState);
        insertWordIntoStack(input, this.leftStack);
        while (!this.leftStack.empty()) {
            this.rightStack.push(this.leftStack.pop());
        }
    }

    private void insertWordIntoStack(final String word, final Stack<Character> stack) {
        if (stack.isEmpty() && Condition.EMPTY_AS_STRING.equals(word)) {
            return;
        }
        word.chars()
                .mapToObj(ch -> (char) ch)
                .forEach(stack::push);
    }

    public TwoStackCondition(final TwoStackCondition twoStackCondition) {
        this(twoStackCondition.getCurrentState(), twoStackCondition.getLeftStack(), twoStackCondition.getRightStack());
    }

    public TwoStackCondition(final MachineState currentState, final Stack<Character> leftStack, final Stack<Character> rightStack) {
        super(currentState);
        this.leftStack.addAll(leftStack);
        this.rightStack.addAll(rightStack);
    }

    @Override
    public Character getCurrentPosition() {
        return rightStack.isEmpty() ? EMPTY : rightStack.peek();
    }

    @Override
    public void useRule(final Rule rule) {
        if (!(rule instanceof TwoStackRule)) {
            throw new IllegalStateException("Only TuringRule can be used on TuringState!");
        }
        val twoStackRule = (TwoStackRule) rule;
        if (!this.rightStack.isEmpty()) {
            this.rightStack.pop();
        }
        insertWordIntoStack(twoStackRule.getWriteRight(), this.rightStack);
        insertWordIntoStack(twoStackRule.getWriteLeft(), this.leftStack);
        if (twoStackRule.isCopyLeftToRight()) {
            val topOfLeftStack = (this.leftStack.isEmpty() ? Condition.EMPTY : this.leftStack.pop()).toString();
            insertWordIntoStack(topOfLeftStack, this.rightStack);
        }
    }

    public List<Character> getCharactersAhead() {
        if (this.rightStack.isEmpty()) {
            return Collections.emptyList();
        }
        val charactersAhead = newArrayList(rightStack);
        charactersAhead.remove(charactersAhead.size() - 1);
        reverse(charactersAhead);
        return charactersAhead;
    }

    public List<Character> getCharactersBehind() {
        if (this.leftStack.isEmpty()) {
            return Collections.emptyList();
        }
        return newArrayList(this.leftStack);
    }
}
