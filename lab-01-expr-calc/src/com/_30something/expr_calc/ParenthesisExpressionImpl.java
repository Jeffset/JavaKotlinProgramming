package com._30something.expr_calc;

public class ParenthesisExpressionImpl implements ParenthesisExpression {

    public ParenthesisExpressionImpl(Expression childExpr) {
        this.childExpr = childExpr;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitParenthesis(this);
    }

    @Override
    public Expression getExpr() {
        return childExpr;
    }

    private final Expression childExpr;
}
