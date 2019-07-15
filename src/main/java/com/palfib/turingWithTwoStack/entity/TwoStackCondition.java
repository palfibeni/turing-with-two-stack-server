package com.palfib.turingWithTwoStack.entity;

import lombok.Getter;
import lombok.val;

import java.util.Stack;

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
        if (!rightStack.isEmpty()) {
            rightStack.pop();
        }
        if (!rightStack.isEmpty() || !Condition.EMPTY_AS_STRING.equals(twoStackRule.getWriteRight())) {
            insertWordIntoStack(twoStackRule.getWriteRight(), this.rightStack);
        }
        if (!leftStack.isEmpty() || !Condition.EMPTY_AS_STRING.equals(twoStackRule.getWriteLeft())) {
            insertWordIntoStack(twoStackRule.getWriteLeft(), this.leftStack);
        }
        if (twoStackRule.isCopyLeftToRight()) {
            this.rightStack.push(this.leftStack.pop());
        }
    }
}
