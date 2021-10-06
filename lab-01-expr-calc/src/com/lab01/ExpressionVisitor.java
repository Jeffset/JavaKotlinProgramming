package com.lab01;

public interface ExpressionVisitor<ResultType> {
    ResultType visitBinaryExpression(BinaryExpression expr);
    ResultType visitLiteral(Literal expr);
    ResultType visitParenthesis(ParenthesisExpression expr);
}
