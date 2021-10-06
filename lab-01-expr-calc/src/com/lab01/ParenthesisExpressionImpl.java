package com.lab01;

public class ParenthesisExpressionImpl implements ParenthesisExpression{
    private final Expression mExpression;

    public ParenthesisExpressionImpl(Expression expression) {
        this.mExpression = expression;
    }

    @Override
    public double compute() {
        return mExpression.compute();
    }

    @Override
    public String debugRepresentation() {
        return String.format("Parenthesis(%s)", mExpression.debugRepresentation());
    }

    @Override
    public int depth() {
        return mExpression.depth() + 1;
    }

    @Override
    public <ResultType> ResultType accept(ExpressionVisitor<ResultType> visitor) {
        return visitor.visitParenthesis(this);
    }

    @Override
    public Expression getExpr() {
        return mExpression;
    }
}
