package com.lab01;

public class DebugRepresentationExpressionVisitor implements ExpressionVisitor {
    public Object visitBinaryExpression(BinaryExpression expr) {
        return new Object();
    }

    public Object visitLiteral(Literal expr) {
        return new Object();
    }

    public Object visitParenthesis(ParenthesisExpression expr) {
        return new Object();
    }
}
