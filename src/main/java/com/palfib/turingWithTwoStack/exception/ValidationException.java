package com.palfib.turingWithTwoStack.exception;

import lombok.Getter;
import java.util.List;
import static java.util.Collections.singletonList;

@Getter
public class ValidationException extends Exception{

    private final List<String> errors;


    public ValidationException(final String error) {
        super();
        this.errors = singletonList(error);
    }

    public ValidationException(final List<String> errors) {
        super();
        this.errors = errors;
    }

    public String getMessage() {
        return errors.stream().reduce("", String::concat);
    }

}
