package com.lab01;

import java.util.Locale;
import java.util.function.Function;

public class BinaryExpressionImpl implements BinaryExpression {
    private final Expression left;
    private final Expression right;
    private final BinaryOperationType operation;

    BinaryExpressionImpl(Expression left, BinaryOperationType operation, Expression right) {
        this.left = left;
        this.operation = operation;
        this.right = right;
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
    public BinaryOperationType getOperation() {
        return operation;
    }

    @Override
    public double compute() {
        switch (operation) {
            case PLUS -> {
                return left.compute() + right.compute();
            }
            case MINUS -> {
                return left.compute() - right.compute();
            }
            case MULTIPLY -> {
                return left.compute() * right.compute();
            }
            case DIVIDE -> {
                return left.compute() / right.compute();
            }
        }

        // Unreachable code
        return 0;
    }

    @Override
    public String debugRepresentation() {
        Function<String, String> capitalize = str -> str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        return String.format("%s(%s, %s)",
                capitalize.apply(operation.toString()), left.debugRepresentation(), right.debugRepresentation());
    }

    @Override
    public int depth() {
        return Math.max(left.depth(), right.depth()) + 1;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return null;
    }
}
