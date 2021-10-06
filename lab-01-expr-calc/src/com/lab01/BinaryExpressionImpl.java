package com.lab01;

import java.util.function.Function;

public class BinaryExpressionImpl implements BinaryExpression {
    private final Expression mLeft;
    private final Expression mRight;
    private final BinaryOperationType mOperation;

    BinaryExpressionImpl(Expression left, BinaryOperationType operation, Expression right) {
        this.mLeft = left;
        this.mOperation = operation;
        this.mRight = right;
    }

    @Override
    public Expression getLeft() {
        return mLeft;
    }

    @Override
    public Expression getRight() {
        return mRight;
    }

    @Override
    public BinaryOperationType getOperation() {
        return mOperation;
    }

    @Override
    public double compute() {
        switch (mOperation) {
            case PLUS -> {
                return mLeft.compute() + mRight.compute();
            }
            case MINUS -> {
                return mLeft.compute() - mRight.compute();
            }
            case MULTIPLY -> {
                return mLeft.compute() * mRight.compute();
            }
            case DIVIDE -> {
                return mLeft.compute() / mRight.compute();
            }
        }

        // Unreachable code
        return 0;
    }

    @Override
    public String debugRepresentation() {
        Function<String, String> capitalize = str -> str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        return String.format("%s(%s, %s)",
                capitalize.apply(mOperation.toString()),
                mLeft.debugRepresentation(),
                mRight.debugRepresentation());
    }

    @Override
    public int depth() {
        return Math.max(mLeft.depth(), mRight.depth()) + 1;
    }

    @Override
    public <ResultType> ResultType accept(ExpressionVisitor<ResultType> visitor) {
        return visitor.visitBinaryExpression(this);
    }
}
