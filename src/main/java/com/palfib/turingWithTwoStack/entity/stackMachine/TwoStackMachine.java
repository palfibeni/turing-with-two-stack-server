package com.palfib.turingWithTwoStack.entity.stackMachine;

import java.util.Stack;

public class TwoStackMachine {

    private final Stack<Character> charactersAhead = new Stack<>();

    private final Stack<Character> charactersBehind = new Stack<>();
}
