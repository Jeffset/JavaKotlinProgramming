package com.lab01;

public interface Expression {
    /**
     * Recursively computes the result of this expression.
     */
    double compute();

    /**
     * Returns string representation of this expression for debugging purposes.
     */
    String debugRepresentation();

    /**
     * Recursively computes the depth of this expression.
     */
    int depth();

    <ResultType> ResultType accept(ExpressionVisitor<ResultType> visitor);
}
