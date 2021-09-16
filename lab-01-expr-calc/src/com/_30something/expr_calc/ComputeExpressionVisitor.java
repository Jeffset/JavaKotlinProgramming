package com._30something.expr_calc;

public class ComputeExpressionVisitor implements ExpressionVisitor {

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        BinOpKind operation = expr.getOperation();
        Double leftRes = (Double) expr.getLeft().accept(this);
        Double rightRes = (Double) expr.getRight().accept(this);
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
