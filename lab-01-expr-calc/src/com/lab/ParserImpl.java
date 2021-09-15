package com.lab;

import java.util.Scanner;
import java.util.Stack;

public class ParserImpl implements Parser {

    @Override
    public Expression parseExpression(String input) throws ExpressionParseException {
        // poland notation
        Scanner scanner = new Scanner(input);
        StringBuilder pol_notation = new StringBuilder();
        Stack<String> stack = new Stack<>();
        while (scanner.hasNext()) {
            String string = scanner.next();
            if (IsOperation(string)) {
                if (string.equals("(")) {
                    stack.push("(");
                } else if (string.equals(")")) {
                    while (!stack.empty() && !stack.peek().equals("(")) {
                        if (stack.peek().equals(")")) {
                            throw new ExpressionParseException();
                        }
                        pol_notation.append(" " + stack.peek());
                        stack.pop();
                    }
                    stack.pop();
                    if (stack.empty()) {
                        throw new ExpressionParseException();
                    }
                } else {
                    while (!stack.empty() && Priority(string) <= Priority(stack.peek())) {
                        pol_notation.append(" " + stack.peek());
                        stack.pop();
                    }
                    stack.push(string);
                }
            } else {
                pol_notation.append(" " + string);
            }
        }
        while (!stack.empty()) {
            if (!IsOperation(stack.peek())) {
                throw new ExpressionParseException();
            }
            pol_notation.append(" " + stack.peek());
            stack.pop();
        }

        // making tree

        Stack<Expression> expressions = new Stack<>();
        Scanner scanner2 = new Scanner(pol_notation.toString());

        while (scanner2.hasNext()) {
            String string = scanner2.next();
            if (IsOperation(string)) {
                Expression second = expressions.peek();
                expressions.pop();
                if (expressions.empty()) {
                    throw new ExpressionParseException();
                }
                Expression first = expressions.peek();
                expressions.pop();
                expressions.push(new BinaryExpressionImpl(first, second, Operation(string)));
            } else if (IsLiteral(string)) {
                Literal literal = new LiteralImpl(Double.parseDouble(string));
                expressions.push(literal);
            } else if (IsVariable(string)) {
                Variable variable = new VariableImpl(string);
                expressions.push(variable);
            } else {
                throw new ExpressionParseException();
            }
        }

        return expressions.peek();
    }

    boolean IsOperation(String string) {
        if (string.equals("+") || string.equals("-") ||
                string.equals("*") || string.equals("/") ||
                string.equals("(") || string.equals(")")) {
            return true;
        }
        return false;
    }

    boolean IsLiteral(String string) {
        char[] char_array = string.toCharArray();
        for (char symbol : char_array) {
            if (!(symbol >= '0' && symbol <= '9')) {
                return false;
            }
        }
        return true;
    }

    boolean IsVariable(String string) {
        char[] char_array = string.toCharArray();
        for (char symbol : char_array) {
            if (!((symbol >= 'a' && symbol <= 'z') ||
                    (symbol >= 'A' && symbol <= 'Z'))) {
                return false;
            }
        }
        return true;
    }

    BinOpKind Operation(String string) throws ExpressionParseException {
        char[] char_array = string.toCharArray();
        switch (char_array[0]) {
            case '+' -> {
                return BinOpKind.ADD;
            }
            case '-' -> {
                return BinOpKind.SUB;
            }
            case '*' -> {
                return BinOpKind.MUL;
            }
            case '/' -> {
                return BinOpKind.DIV;
            }
            default -> {
                throw new ExpressionParseException();
            }
        }
    }

    int Priority(String string) {
        if (string.equals("+") || string.equals("-")) {
            return 1;
        } else if (string.equals("(")) {
            return 0;
        }
        return 2;
    }
}






