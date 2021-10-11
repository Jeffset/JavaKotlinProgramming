package com._30something.expr_calc;

import java.util.Map;

public class ComputeExpressionVisitor implements ExpressionVisitor<Double> {

    Map<String, Double> map;

    public ComputeExpressionVisitor(Map<String, Double> map) {
        this.map = map;
    }

    @Override
    public Double visitBinaryExpression(BinaryExpression expr) {
        BinOpKind operation = expr.getOperation();
        Double leftRes = expr.getLeft().accept(this);
        Double rightRes = expr.getRight().accept(this);
        switch (operation) {
            case ADD -> {
                return leftRes + rightRes;
            }
            case SUBTRACT -> {
                return leftRes - rightRes;
            }
            case MULTIPLY -> {
                return leftRes * rightRes;
            }
            case DIVIDE -> {
                if (rightRes == 0) throw new ArithmeticException("Division by zero found");
                return leftRes / rightRes;
            }
            case DEFAULT -> {
                return 0.;
            }
        }
        return null;
    }

    @Override
    public Double visitLiteral(Literal expr) {
        return expr.getValue();
    }

    @Override
    public Double visitParenthesis(ParenthesisExpression expr) {
        return expr.getExpr().accept(this);
    }

    @Override
    public Double visitVariable(Variable expr) {
        return map.get(expr.getName());
    }
}
