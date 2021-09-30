package com.expression;

public interface Expression {
    double compute();
    String debugRepresentation();
    Object accept(ExpressionVisitor visitor);

    // Getters
    Expression getLeft();
    Expression getRight();
    UniqueOperator getValue();
}


