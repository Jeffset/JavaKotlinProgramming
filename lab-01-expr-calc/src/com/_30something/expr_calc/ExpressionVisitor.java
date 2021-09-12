package com._30something.expr_calc;

public interface ExpressionVisitor {
    Object visitBinaryExpression(BinaryExpression expr);
    Object visitLiteral(Literal expr);
    Object visitParenthesis(ParenthesisExpression expr);
}


