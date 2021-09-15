package com._30something.expr_calc;

public class ParenthesisExpressionImpl implements ParenthesisExpression {

    public ParenthesisExpressionImpl(Expression child_expr) {
        this.child_expr = child_expr;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitParenthesis(this);
    }

    @Override
    public Expression getExpr() {
        return child_expr;
    }

    private Expression child_expr = null;
}
