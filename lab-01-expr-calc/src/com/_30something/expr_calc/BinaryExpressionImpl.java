package com._30something.expr_calc;

public class BinaryExpressionImpl implements BinaryExpression {

    @Override
    public Expression getLeft() {
        return left;
    }

    @Override
    public Expression getRight() {
        return right;
    }

    @Override
    public BinOpKind getOperation() {
        return operation;
    }

    @Override
    public double compute() {
        if (operation == null) return 0;
        double left_res = getLeft().compute();
        double right_res = getRight().compute();
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
            default -> {
                return 0;
            }
        }
    }

    @Override
    public String debugRepresentation() {
        // TODO
        return null;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        visitor.visitBinaryExpression(this);
        return null;
    }

    private final Expression left = null;
    private final Expression right = null;
    private final BinOpKind operation = null;

}
