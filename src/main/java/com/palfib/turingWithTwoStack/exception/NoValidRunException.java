package com.palfib.turingWithTwoStack.exception;

import com.palfib.turingWithTwoStack.entity.Machine;

public class NoValidRunException extends RuntimeException {

    private static final String MESSAGE_FORMAT = "No valid run found for input (%s)";

    private final String input;

    public NoValidRunException(final String input) {
        this.input = input;
    }

    public String getMessage() {
        return String.format(MESSAGE_FORMAT, input);
    }
}
