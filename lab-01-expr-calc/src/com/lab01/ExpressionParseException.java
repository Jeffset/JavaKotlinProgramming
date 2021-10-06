package com.lab01;

public class ExpressionParseException extends Exception {
    private final String message;

    ExpressionParseException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
