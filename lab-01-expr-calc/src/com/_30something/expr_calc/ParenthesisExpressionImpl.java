package com._30something.expr_calc;

public class ParenthesisExpressionImpl implements ParenthesisExpression {

    @Override
    public Object accept(ExpressionVisitor visitor) {
        visitor.visitParenthesis(this);
        return null;
    }

    @Override
    public Expression getExpr() {
        return child_expr;
    }

    private Expression child_expr;
}
