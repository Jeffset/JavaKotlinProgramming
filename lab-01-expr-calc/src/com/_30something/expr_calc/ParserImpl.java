package com._30something.expr_calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class ParserImpl implements Parser {

    public enum CharTypes {
        LITERAL,
        NUMBER,
        OPERATOR,
        DEFAULT,
    }

    public CharTypes charType(char c) {
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) return CharTypes.LITERAL;
        if ((c >= '0' && c <= '9') || c == '.') return CharTypes.NUMBER;
        if (c == '-' || c == '+' || c == '*' || c == '/' || c == '(' || c == ')') return CharTypes.OPERATOR;
        return CharTypes.DEFAULT;
    }

    public static class Token {
        public String string;
        public CharTypes type;

        public Token(String string, CharTypes type) {
            this.string = string;
            this.type = type;
        }
    }

    public List<Token> tokenize(String input) {
        List<Token> tokens_list = new ArrayList<>();
        Token temp_token;
        char[] chars = input.toCharArray();
        for (int current_ptr = 0; current_ptr < chars.length; ) {
            CharTypes token_type = charType(chars[current_ptr]);
            if (token_type == CharTypes.LITERAL || token_type == CharTypes.NUMBER) {
                StringBuilder token_name = new StringBuilder();
                while (current_ptr < chars.length && charType(chars[current_ptr]) == token_type) {
                    token_name.append(chars[current_ptr]);
                    current_ptr++;
                }
                temp_token = new Token(token_name.toString(), token_type);
                tokens_list.add(temp_token);
            } else {
                temp_token = new Token(Character.toString(chars[current_ptr]), token_type);
                tokens_list.add(temp_token);
                current_ptr++;
            }
        }
        return tokens_list;
    }

    public List<Token> verifyTokens(List<Token> tokens) throws ExpressionParseException {
        List<Token> new_tokens = new ArrayList<>();
        Token past_token = new Token("", CharTypes.DEFAULT);
        int left_brackets = 0;
        boolean unary_sign = false;
        boolean unary_minus = false;
        for (Token token : tokens) {
            if (Objects.equals(token.string, " ")) {
                continue;
            }
            if (token.type == CharTypes.DEFAULT || Objects.equals(token.string, ".")) {
                throw new ExpressionParseException("Unexpected token found: '" + token.string + "'");
            }
            if (token.type == CharTypes.OPERATOR) {
                if (Objects.equals(token.string, "(")) {
                    left_brackets++;
                    new_tokens.add(token);
                    unary_minus = unary_sign = false;
                } else if (Objects.equals(token.string, ")")) {
                    left_brackets--;
                    if (left_brackets < 0) {
                        throw new ExpressionParseException("Overflow with right brackets found");
                    }
                    new_tokens.add(token);
                    unary_minus = unary_sign = false;
                } else {
                    if (past_token.type == CharTypes.OPERATOR && !Objects.equals(past_token.string, ")") &&
                            !Objects.equals(past_token.string, "(")) {
                        throw new ExpressionParseException("Wrong order of operands found");
                    } else if (Objects.equals(past_token.string, "(")) {
                        if (Objects.equals(token.string, "*") || Objects.equals(token.string, "/")) {
                            throw new ExpressionParseException("Wrong order of operands found");
                        } else {
                            if (unary_sign) {
                                throw new ExpressionParseException("Wrong order of operands found");
                            }
                            unary_sign = true;
                            if (Objects.equals(token.string, "-")) {
                                unary_minus = true;
                            }
                        }
                    } else {
                        new_tokens.add(token);
                    }
                }
            } else if (token.type == CharTypes.LITERAL) {
                if (unary_minus) {
                    new_tokens.add(new Token("-" + token.string, token.type));
                    unary_minus = unary_sign = false;
                } else {
                    new_tokens.add(token);
                }
            } else {
                if (token.string.chars().filter(c -> c == '.').count() > 1) {
                    throw new ExpressionParseException("Two dots in float number found: '" + token.string + "'");
                } else {
                    if (unary_minus) {
                        new_tokens.add(new Token("-" + token.string, token.type));
                        unary_minus = unary_sign = false;
                    } else {
                        new_tokens.add(token);
                    }
                }
            }
            past_token = token;
        }
        if (left_brackets > 0) {
            throw new ExpressionParseException("Overflow with left brackets found");
        }
        return new_tokens;
    }

    public List<Token> buildPolishNotation(List<Token> tokens) {
        Stack<Token> operators = new Stack<>();
        List<Token> new_list = new ArrayList<>();
        for (Token token : tokens) {
            if (token.type == CharTypes.OPERATOR) {
                if (Objects.equals(token.string, "(")) {
                    operators.add(token);
                    new_list.add(token);
                } else if (Objects.equals(token.string, ")")) {
                    while (!Objects.equals(operators.peek().string, "(")) {
                        new_list.add(operators.peek());
                        operators.pop();
                    }
                    operators.pop();
                    new_list.add(token);
                } else if (Objects.equals(token.string, "*") || Objects.equals(token.string, "/")) {
                    while (!operators.empty() && (Objects.equals(operators.peek().string, "*") ||
                            Objects.equals(operators.peek().string, "/"))) {
                        new_list.add(operators.peek());
                        operators.pop();
                    }
                    operators.push(token);
                } else {
                    while (!operators.empty() && !Objects.equals(operators.peek().string, "(")) {
                        new_list.add(operators.peek());
                        operators.pop();
                    }
                    operators.push(token);
                }
            } else {
                new_list.add(token);
            }
        }
        while (!operators.empty()) {
            new_list.add(operators.peek());
            operators.pop();
        }
        return new_list;
    }

    public Expression buildExpression(List<Token> tokens) throws ExpressionParseException {
        Stack<Expression> expressions = new Stack<>();
        for (Token token : tokens) {
            if (token.type == CharTypes.OPERATOR) {
                if (Objects.equals(token.string, ")")) {
                    Expression lower_expr = expressions.peek();
                    expressions.pop();
                    expressions.push(new ParenthesisExpressionImpl(lower_expr));
                } else if (!Objects.equals(token.string, "(")) {
                    Expression right_expr = expressions.peek();
                    expressions.pop();
                    Expression left_expr = expressions.peek();
                    expressions.pop();
                    BinOpKind operation;
                    if (Objects.equals(token.string, "+")) operation = BinOpKind.ADD;
                    else if (Objects.equals(token.string, "-")) operation = BinOpKind.SUBTRACT;
                    else if (Objects.equals(token.string, "*")) operation = BinOpKind.MULTIPLY;
                    else operation = BinOpKind.DIVIDE;
                    expressions.push(new BinaryExpressionImpl(left_expr, right_expr, operation));
                }
            } else {
                expressions.push(new LiteralImpl(token.string, token.type));
            }
        }
        if (expressions.size() > 1) {
            throw new ExpressionParseException("Wrong order of operands found");
        }
        return expressions.peek();
    }

    @Override
    public Expression parseExpression(String input) throws ExpressionParseException {
        // TODO: Create map with literals
        List<Token> raw_tokens = tokenize(input);
        List<Token> verified_tokens = verifyTokens(raw_tokens);
        List<Token> polish_notation_tokens = buildPolishNotation(verified_tokens);
        return buildExpression(polish_notation_tokens);
    }
}
