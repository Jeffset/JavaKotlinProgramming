package com.lab;

public class ComputeExpressionVisitor implements ExpressionVisitor {

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        double l_value = (Double) expr.getLeft().accept(new ComputeExpressionVisitor());
        double r_value = (Double) expr.getRight().accept(new ComputeExpressionVisitor());
        BinOpKind operation = expr.getOperation();
        switch (operation) {
            case DIV -> {
                return l_value / r_value;
            }
            case MUL -> {
                return l_value * r_value;
            }
            case SUB -> {
                return l_value - r_value;
            }
            case ADD -> {
                return l_value + r_value;
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return expr.getValue();
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expr) {
        return expr.getExpr();
    }
}
