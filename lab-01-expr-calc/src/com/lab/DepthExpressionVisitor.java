package com.lab;

public class DepthExpressionVisitor implements ExpressionVisitor {

    private DepthExpressionVisitor() {}

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

        return 1 + (Integer) expr.getExpr().accept(new DepthExpressionVisitor());
    }

    @Override
    public Object visitVariable(Variable expr) {
        return 1;
    }

    public static final DepthExpressionVisitor INSTANCE = new DepthExpressionVisitor();
}
