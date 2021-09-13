package com._30something.expr_calc;

public class ParenthesisExpressionImpl implements ParenthesisExpression {

    @Override
    public double compute() {
        return child_expr.compute();
    }

    @Override
    public String debugRepresentation() {
        // TODO
        return null;
    }

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
