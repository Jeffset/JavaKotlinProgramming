package com.lab;

public class ToStringVisitor implements ExpressionVisitor {

    private ToStringVisitor() {}

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        String left = (String) expr.getLeft().accept(new ToStringVisitor());
        String right = (String) expr.getRight().accept(new ToStringVisitor());
        BinOpKind operation = expr.getOperation();
        switch (operation) {
            case ADD -> {
                return left + " + " + right;
            }
            case SUB -> {
                return left + " - " + right;
            }
            case MUL -> {
                return left + " * " + right;
            }
            case DIV -> {
                return left + " / " + right;
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return Double.toString(expr.getValue());
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expr) {
        return "(" + (String) expr.getExpr().accept(new ToStringVisitor()) + ")";
    }

    @Override
    public Object visitVariable(Variable expr) {
        return expr.getVariable();
    }

    public static final ToStringVisitor INSTANCE = new ToStringVisitor();
}
