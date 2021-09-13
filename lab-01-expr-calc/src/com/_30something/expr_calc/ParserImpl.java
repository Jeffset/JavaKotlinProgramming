package com._30something.expr_calc;

import java.util.Objects;

public class ParserImpl implements Parser {
    @Override
    public Expression parseExpression(String input) throws ExpressionParseException {
        if (Objects.equals(input, "hello")) throw new ExpressionParseException("nononono");
        else return null;
    }
}
