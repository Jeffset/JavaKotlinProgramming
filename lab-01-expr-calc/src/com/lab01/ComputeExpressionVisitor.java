package com.lab01;

public class ComputeExpressionVisitor implements ExpressionVisitor<Double> {
    public Double visitBinaryExpression(BinaryExpression expr) {
        switch (expr.getOperation()) {
            case PLUS -> {
                return expr.getLeft().accept(this) + expr.getRight().accept(this);
            }
            case MINUS -> {
                return expr.getLeft().accept(this) - expr.getRight().accept(this);
            }
            case MULTIPLY -> {
                return expr.getLeft().accept(this) * expr.getRight().accept(this);
            }
            case DIVIDE -> {
                return expr.getLeft().accept(this) / expr.getRight().accept(this);
            }
        }

        // Unreachable code
        return (double)0;
    }

    public Double visitLiteral(Literal expr) {
        return expr.getValue();
    }

    public Double visitParenthesis(ParenthesisExpression expr) {
        return expr.getExpr().accept(this);
    }
}
