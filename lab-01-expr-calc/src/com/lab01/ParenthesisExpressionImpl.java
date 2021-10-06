package com.lab01;

public class ParenthesisExpressionImpl implements ParenthesisExpression{
    private final Expression expression;

    public ParenthesisExpressionImpl(Expression expression) {
        this.expression = expression;
    }

    @Override
    public double compute() {
        return expression.compute();
    }

    @Override
    public String debugRepresentation() {
        return String.format("Parenthesis(%s)", expression.debugRepresentation());
    }

    @Override
    public int depth() {
        return expression.depth() + 1;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return null;
    }

    @Override
    public Expression getExpr() {
        return expression;
    }
}
