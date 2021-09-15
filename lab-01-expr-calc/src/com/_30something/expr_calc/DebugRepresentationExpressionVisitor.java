package com._30something.expr_calc;

public class DebugRepresentationExpressionVisitor implements ExpressionVisitor {

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        if (expr.getOperation() == BinOpKind.ADD) {
            return "add(" + expr.getLeft().accept(this) + ", " + expr.getRight().accept(this) + ")";
        } else if (expr.getOperation() == BinOpKind.SUBTRACT) {
            return "sub(" + expr.getLeft().accept(this) + ", " + expr.getRight().accept(this) + ")";
        } else if (expr.getOperation() == BinOpKind.MULTIPLY) {
            return "mul(" + expr.getLeft().accept(this) + ", " + expr.getRight().accept(this) + ")";
        } else {
            return "div(" + expr.getLeft().accept(this) + ", " + expr.getRight().accept(this) + ")";
        }
    }

    @Override
    public Object visitLiteral(Literal expr) {
        LiteralImpl casted_expr = (LiteralImpl)expr;
        if (casted_expr.getType() == ParserImpl.CharTypes.LITERAL) {
            return "var[" + casted_expr.getString() + "]";
        } else {
            return "'" + casted_expr.getValue() + "'";
        }
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expr) {
        return "paran-expr(" + expr.getExpr().accept(this) + ")";
    }
}
