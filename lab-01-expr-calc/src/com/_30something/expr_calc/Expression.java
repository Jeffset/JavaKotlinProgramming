package com._30something.expr_calc;

public interface Expression {
    <T> T accept(ExpressionVisitor<T> visitor);
}
