package com.expression;

public interface Parser {
    Expression parseExpression(String input) throws ExpressionParseException;

    static class ExpressionParseException extends Exception {
        public ExpressionParseException(String s) {
            super(s);
        }
    }
}
