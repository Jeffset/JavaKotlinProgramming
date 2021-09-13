package com._30something.expr_calc;

public interface Expression {

    /**
     * Recursively computes the result of this expression.
     */
    double compute();

    /**
     * Returns string representation of this expression for debugging purposes.
     */
    String debugRepresentation();

    Object accept(ExpressionVisitor visitor);

}
