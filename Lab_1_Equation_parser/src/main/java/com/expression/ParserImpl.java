package com.expression;

import java.util.Stack;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;

import static java.lang.Math.abs;

public class ParserImpl implements Parser {
    private Map<String, Double> dict_of_variables = new HashMap<String, Double>();
    @Override
    public Expression parseExpression(String input) throws ExpressionParseException {
        if (!IsExpressionCorrect(input)) {
            throw new ExpressionParseException("Input is not correct.");
        } else {
            return parseExpression(input, null);
        }
    }

    /**
     * Builds a subtree of binary tree with given root.
     *
     * @param input
     * @param parent parent of the root of the subtree.
     * @return root of the subtree.
     */
    private Expression parseExpression(String input, Expression parent) {
        input = DetermineOuterBraces(input);
        if (input.length() == 1 && Character.isAlphabetic(input.charAt(0))) {
            if (dict_of_variables.containsKey(input)) {
                return new ExpressionImpl(null, null, parent, new LiteralImpl(input, dict_of_variables.get(input)));
            } else {
                Expression expression = new ExpressionImpl(null, null, parent, new LiteralImpl(input));
                expression.getValue().setValue();
                dict_of_variables.put(input, expression.getValue().getValue());
                return expression;
            }
        }
        try {
            double return_value = Double.parseDouble(input);
            return new ExpressionImpl(null, null, parent, new LiteralImpl(Double.parseDouble(input)));
        } catch (NumberFormatException e) {
            String left = "";
            String right = "";
            String[] list_of_values = input.split("");
            int remember_max = -2;
            int remember_s_index = -1;
            String remember_s = "";
            boolean work_in_braces = false;
            Stack<Character> braces_stack = new Stack<Character>();
            for (int i = 0; i < list_of_values.length; i++) {
                String s = String.valueOf(input.charAt(i));
                if (s.equals(")") && !braces_stack.empty() && braces_stack.peek() == '(') {
                    braces_stack.pop();
                    work_in_braces = !braces_stack.empty();
                } else if (s.equals("(") || work_in_braces) {
                    if (s.equals("(")) {
                        braces_stack.push('(');
                    }
                    work_in_braces = true;
                } else {
                    if (("/* -+").contains(s) && (("/* -+").indexOf(s) - remember_max >= 2
                            || abs(("/* -+").indexOf(s) - remember_max) == 1)) {
                        remember_max = ("/* -+").indexOf(s);
                        remember_s = String.valueOf(("/* -+").charAt(remember_max));
                        remember_s_index = i;
                    }
                }
            }
            for (int j = 0; j < remember_s_index; j++) {
                left = left.concat(list_of_values[j]);
            }
            for (int j = remember_s_index + 1; j < list_of_values.length; j++) {
                right = right.concat(list_of_values[j]);
            }
            ExpressionImpl returned_expression = new ExpressionImpl(null, null, parent,
                    new BinaryExpressionImpl(remember_s));
            returned_expression.setLeft(parseExpression(DetermineOuterBraces(left), returned_expression));
            returned_expression.setRight(parseExpression(DetermineOuterBraces(right), returned_expression));
            return returned_expression;
        }
    }

    /**
     * Delete outer braces, i.e. '(x+5)' after transformation will be 'x+5'.
     *
     * @param input string equation.
     * @return string equation without outer braces.
     */
    private static String DetermineOuterBraces(String input) {
        if (input.charAt(0) == '(' && input.charAt(input.length() - 1) == ')') {
            Stack<Character> stack = new Stack<Character>();
            for (int i = 1; i < input.length() - 1; i++) {
                if (input.charAt(i) == '(') {
                    stack.push('(');
                } else if (input.charAt(i) == ')') {
                    if (!stack.empty() && stack.peek() == '(') {
                        stack.pop();
                    } else {
                        stack.push(')');
                    }
                }
            }
            if (stack.empty()) {
                input = input.substring(1, input.length() - 1);
            }
        }
        return input;
    }

    /**
     * In the expression only lowercase letters (and the name of variable contains consists of only one alphabetic
     * character). Also, here we check position of braces. Numbers can be written only as a decimal fraction without
     * prefixes.
     *
     * @param input string with expression (WITHOUT SPACES).
     * @return true, if input is a correct expression.
     */
    private boolean IsExpressionCorrect(String input) {
        if (Pattern.matches("[a-z0-9\\+\\-\\*/\\(\\)]*", input)) {
            Stack<Character> stack = new Stack<Character>();
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == '(') {
                    stack.push('(');
                } else if (input.charAt(i) == ')') {
                    if (!stack.empty() && stack.peek() == '(') {
                        stack.pop();
                    } else {
                        stack.push(')');
                    }
                }
                if (Character.isAlphabetic(input.charAt(i)) && i < input.length() - 1 &&
                        Character.isAlphabetic(input.charAt(i+1))) {
                    return false;
                }
            }
            return stack.empty();
        }
        return false;
    }
}
