package com.github.iamhi.hizone.terreplein.in.exceptions;

import java.util.Map;

public class ValidationErrorThrowable extends Throwable {

    private final Map<String, String > errors;

    public ValidationErrorThrowable(Map<String, String > errors) {
        super("Validation error");

        this.errors = errors;
    }

    public Map<String, String > getErrors() {
        return this.errors;
    }
}
