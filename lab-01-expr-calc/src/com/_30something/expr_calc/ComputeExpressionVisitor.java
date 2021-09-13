package com._30something.expr_calc;

public class ComputeExpressionVisitor implements ExpressionVisitor {

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        BinOpKind operation = expr.getOperation();
        Double left_res = (Double) expr.getLeft().accept(this);
        Double right_res = (Double) expr.getRight().accept(this);
        switch (operation) {
            case ADD -> {
                return left_res + right_res;
            }
            case SUBTRACT -> {
                return left_res - right_res;
            }
            case MULTIPLY -> {
                return left_res * right_res;
            }
            case DIVIDE -> {
                if (right_res == 0) throw new ArithmeticException("Division by zero");
                return left_res / right_res;
            }
            case DEFAULT -> {
                return 0;
            }
        }
        return null;
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return expr.getValue();
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expr) {
        return expr.getExpr().accept(this);
    }
}
