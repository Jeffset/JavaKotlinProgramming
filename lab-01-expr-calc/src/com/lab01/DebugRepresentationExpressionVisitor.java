package com.lab01;

import java.util.function.Function;

public class DebugRepresentationExpressionVisitor implements ExpressionVisitor<String> {
    public String visitBinaryExpression(BinaryExpression expr) {
        Function<String, String> capitalize = str -> str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        return String.format("%s(%s, %s)",
                capitalize.apply(expr.getOperation().toString()),
                expr.getLeft().accept(this),
                expr.getRight().accept(this));
    }

    public String visitLiteral(Literal expr) {
        return expr.getName().isEmpty() ?
                String.format(expr.getValue() == (long) expr.getValue() ? "'%.0f'": "'%s'", expr.getValue())
                                                                        : String.format("[%s]", expr.getName());
    }

    public String visitParenthesis(ParenthesisExpression expr) {
        return String.format("Parenthesis(%s)", expr.getExpr().accept(this));
    }
}
