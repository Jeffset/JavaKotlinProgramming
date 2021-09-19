package com._30something.expr_calc;

public interface Expression {
    Object accept(ExpressionVisitor visitor);
}
