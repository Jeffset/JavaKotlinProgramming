package com._30something.expr_calc;

public interface BinaryExpression extends Expression {
    Expression getLeft();
    Expression getRight();
    BinOpKind getOperation();
}
