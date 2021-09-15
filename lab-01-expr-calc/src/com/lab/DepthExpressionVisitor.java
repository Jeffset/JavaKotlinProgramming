package com.lab;

public class DepthExpressionVisitor implements ExpressionVisitor {

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        return Math.max((Integer) expr.getLeft().accept(new DepthExpressionVisitor()),
                (Integer) expr.getRight().accept(new DepthExpressionVisitor())) + 1;
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return 1;
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expr) {
        return null;
    }

    @Override
    public Object visitVariable(Variable expr) {
        return 1;
    }
}
