package com.lab;

public interface Parser {
    /** * [Такой комментарий к методу/классу/полю называетсяjava-doc -
     * распознается IDE как документация] * * Parses expression from the string. * If the string doesn't
     represent a valid expression, then * throws ExpressionParseException.
     * * @param input the input string. * @return
     parsed expression tree. */
    Expression parseExpression(String input) throws ExpressionParseException;
}

