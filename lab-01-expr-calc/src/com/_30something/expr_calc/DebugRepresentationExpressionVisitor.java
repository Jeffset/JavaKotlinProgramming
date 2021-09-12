package com._30something.expr_calc;

public class DebugRepresentationExpressionVisitor implements ExpressionVisitor {

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

//    double result = (Double) expression.accept(new ComputeExpressionVisitor());
//    String debug = (String) expression.accept(new DebugRepresentationExpressionVisitor());
