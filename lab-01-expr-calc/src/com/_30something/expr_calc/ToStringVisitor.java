package com._30something.expr_calc;

public class ToStringVisitor implements ExpressionVisitor<String> {

    public static final ToStringVisitor INSTANCE = new ToStringVisitor();

    private ToStringVisitor() {}

    @Override
    public String visitBinaryExpression(BinaryExpression expr) {
        String leftRes = expr.getLeft().accept(this);
        String rightRes = expr.getRight().accept(this);
        String operation;
        if (expr.getOperation() == BinOpKind.ADD) {
            operation = "+";
        } else if (expr.getOperation() == BinOpKind.SUBTRACT) {
            operation = "-";
        } else if (expr.getOperation() == BinOpKind.MULTIPLY) {
            operation = "*";
        } else {
            operation = "/";
        }
        return leftRes + " " + operation + " " + rightRes;
    }

    @Override
    public String visitLiteral(Literal expr) {
        return Double.toString(expr.getValue());
    }

    @Override
    public String visitParenthesis(ParenthesisExpression expr) {
        return "(" + expr.getExpr().accept(this) + ")";
    }

    @Override
    public String visitVariable(Variable expr) {
        return expr.getName();
    }
}
