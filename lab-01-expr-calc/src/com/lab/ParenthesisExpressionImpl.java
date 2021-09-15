package com.lab;

public class ParenthesisExpressionImpl implements ParenthesisExpression {

    ParenthesisExpressionImpl(Expression expression) {
        expression_  = expression;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitParenthesis(this);
    }

    @Override
    public Expression getExpr() {
        return expression_;
    }

    private Expression expression_;
}
