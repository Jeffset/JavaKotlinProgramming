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
    public Object accept(ExpressionVisitor visitor) {
        visitor.visitBinaryExpression(this);
        return null;
    }

    private final Expression left = null;
    private final Expression right = null;
    private final BinOpKind operation = BinOpKind.DEFAULT;
}
