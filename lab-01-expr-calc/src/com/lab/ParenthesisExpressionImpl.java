package com.lab;

public class ParenthesisExpressionImpl implements ParenthesisExpression {

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitParenthesis(this);
    }

    @Override
    public Expression getExpr() {
        return null;
    }
}
