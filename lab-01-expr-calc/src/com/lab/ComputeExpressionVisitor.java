package com.lab;

import java.util.Map;

public class ComputeExpressionVisitor implements ExpressionVisitor {

    private final Map<String, Double> variables;

    ComputeExpressionVisitor(Map<String, Double> map) {
        variables = map;
    }

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        double l_value = (Double) expr.getLeft().accept(this);
        double r_value = (Double) expr.getRight().accept(this);
        BinOpKind operation = expr.getOperation();
        switch (operation) {
            case DIV -> {
                if (r_value - 0.0 < 1e-9) {
                    throw new ArithmeticException("division by zero");
                }
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
        return expr.getExpr().accept(this);
    }

    @Override
    public Object visitVariable(Variable expr) {
        return variables.get(expr.getVariable());
    }
}
