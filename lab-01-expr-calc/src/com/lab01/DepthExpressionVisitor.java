package com.lab01;

import java.util.function.Function;

public class DepthExpressionVisitor implements ExpressionVisitor<Integer> {
    public Integer visitBinaryExpression(BinaryExpression expr) {
        return Math.max(expr.getLeft().accept(this), expr.getRight().accept(this)) + 1;
    }

    public Integer visitLiteral(Literal expr) {
        return 1;
    }

    public Integer visitParenthesis(ParenthesisExpression expr) {
        return expr.getExpr().accept(this) + 1;
    }
}