package com._30something.expr_calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParserImpl implements Parser {

    @Override
    public Expression parseExpression(String input) throws ExpressionParseException {
        List<String> parsing_list = new ArrayList<>();
        char[] chars = input.toCharArray();
        String temp_string;
        for (char symbol : chars) {

        }
        if (Objects.equals(input, "hello")) throw new ExpressionParseException("nononono");
        else return null;
    }
}
