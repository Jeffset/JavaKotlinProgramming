package com.expression;

public interface UniqueOperator {
    /**
     * An interface for working with BinaryOperators and Literals
     * that are stored as the value_ of the ExpressionImpl class.
     */
    double compute(UniqueOperator left, UniqueOperator right);
    double getValue();
    void setValue();

    Object accept(ExpressionVisitor visitor);
}
