package com._30something.expr_calc;

public class BinaryExpressionImpl implements BinaryExpression {

    public BinaryExpressionImpl(Expression left, Expression right, BinOpKind operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

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
        return visitor.visitBinaryExpression(this);
    }

    private final Expression left;
    private final Expression right;
    private final BinOpKind operation;
}
