package com.lab;

import static com.lab.BinOpKind.SUB;

public class BinaryExpressionImpl implements BinaryExpression {

    BinaryExpressionImpl(Expression left, Expression right, BinOpKind operation) {
        left_ = left;
        right_ = right;
        operation_ = operation;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitBinaryExpression(this);
    }

    @Override
    public Expression getLeft() {
        return left_;
    }

    @Override
    public Expression getRight() {
        return right_;
    }

    @Override
    public BinOpKind getOperation() {
        return operation_;
    }


    private final BinOpKind operation_;
    private final Expression left_;
    private final Expression right_;
}
