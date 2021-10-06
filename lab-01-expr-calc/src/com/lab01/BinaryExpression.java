package com.lab01;

public interface BinaryExpression extends Expression {
    Expression getLeft();
    Expression getRight();
    BinaryOperationType getOperation();
}
