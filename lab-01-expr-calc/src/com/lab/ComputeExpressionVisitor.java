package com.lab;

import java.util.HashMap;

public class ComputeExpressionVisitor implements ExpressionVisitor {

    ComputeExpressionVisitor(HashMap<String, Double> map) {
        map_ = map;
    }

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        double l_value = (Double) expr.getLeft().accept(new ComputeExpressionVisitor(map_));
        double r_value = (Double) expr.getRight().accept(new ComputeExpressionVisitor(map_));
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
        return expr.getExpr().accept(new ComputeExpressionVisitor(map_));
    }

    @Override
    public Object visitVariable(Variable expr) {
        return map_.get(expr.getVariable());
    }

    private final HashMap<String, Double> map_;

}
