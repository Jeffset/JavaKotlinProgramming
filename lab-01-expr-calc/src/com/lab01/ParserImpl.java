package com.lab01;

import java.util.ArrayList;
import java.util.List;

public class ParserImpl implements Parser {
    public Expression parseExpression(String input) throws ExpressionParseException {
        if (input.isEmpty()) {
            throw new ExpressionParseException("Empty expression");
        }

        int balance = 0;
        int firstTopLevelSecondaryOperatorIdx = -1;
        for (int i = 0; i < input.length(); ++i) {
            switch (input.charAt(i)) {
                case '(' -> ++balance;
                case ')' -> --balance;
                case '+', '-' -> {
                    if (balance == 0) {
                        Expression left = parseExpression(input.substring(0, i - 1));
                        BinaryOperationType operation = input.charAt(i) == '+' ?
                                BinaryOperationType.PLUS : BinaryOperationType.MINUS;
                        Expression right = parseExpression(input.substring(i + 2));
                        return new BinaryExpressionImpl(left, operation, right);
                    }
                }
                case '*', '/' -> {
                    if (balance == 0 && firstTopLevelSecondaryOperatorIdx == -1) {
                        firstTopLevelSecondaryOperatorIdx = i;
                    }
                }
            }
        }

        if (firstTopLevelSecondaryOperatorIdx == -1) {
            if (input.charAt(0) == '(') {
                return new ParenthesisExpressionImpl(parseExpression(input.substring(1, input.length() - 1)));
            } else {
                try {
                    return new LiteralImpl(Double.parseDouble(input));
                } catch (NumberFormatException e) {
                    return new LiteralImpl(input);
                }
            }
        } else {
            Expression left = parseExpression(input.substring(0, firstTopLevelSecondaryOperatorIdx - 1));
            BinaryOperationType operation = input.charAt(firstTopLevelSecondaryOperatorIdx) == '*' ?
                    BinaryOperationType.MULTIPLY : BinaryOperationType.DIVIDE;
            Expression right = parseExpression(input.substring(firstTopLevelSecondaryOperatorIdx + 2));
            return new BinaryExpressionImpl(left, operation, right);
        }
    }
}
