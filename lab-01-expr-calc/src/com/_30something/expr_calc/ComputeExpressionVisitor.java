package com._30something.expr_calc;

public class ComputeExpressionVisitor implements ExpressionVisitor {

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        return null;
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return null;
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expr) {
        return null;
    }
}
