package com._30something.expr_calc;

public class DebugRepresentationExpressionVisitor implements ExpressionVisitor<String> {

    @Override
    public String visitBinaryExpression(BinaryExpression expr) {
        String leftRes = expr.getLeft().accept(this);
        String rightRes = expr.getRight().accept(this);
        String operationPrefix;
        if (expr.getOperation() == BinOpKind.ADD) {
            operationPrefix = "add";
        } else if (expr.getOperation() == BinOpKind.SUBTRACT) {
            operationPrefix = "sub";
        } else if (expr.getOperation() == BinOpKind.MULTIPLY) {
            operationPrefix = "mul";
        } else {
            operationPrefix = "div";
        }
        return operationPrefix + "(" + leftRes + ", " + rightRes + ")";
    }

    @Override
    public String visitLiteral(Literal expr) {
        return "'" + expr.getValue() + "'";
    }

    @Override
    public String visitParenthesis(ParenthesisExpression expr) {
        return "paran-expr(" + expr.getExpr().accept(this) + ")";
    }

    @Override
    public String visitVariable(Variable expr) {
        return "var[" + expr.getName() + "]";
    }
}
