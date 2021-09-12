package com._30something.expr_calc;

public interface ParenthesisExpression extends Expression {
    Expression getExpr();  // Whatever the expression is inside ().
}
