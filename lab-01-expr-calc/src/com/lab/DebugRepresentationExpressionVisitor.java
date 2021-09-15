package com.lab;

public class DebugRepresentationExpressionVisitor implements ExpressionVisitor {


    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        String l_debug = (String) expr.getLeft().accept(new DebugRepresentationExpressionVisitor());
        String r_debug = (String) expr.getRight().accept(new DebugRepresentationExpressionVisitor());
        BinOpKind operation = expr.getOperation();
        switch (operation) {
            case ADD -> {
                return "add(" + l_debug + ", " + r_debug + ")";
            }
            case SUB -> {
                return "sub(" + l_debug + ", " + r_debug + ")";
            }
            case MUL -> {
                return "mul(" + l_debug + ", " + r_debug + ")";
            }
            case DIV -> {
                return "div(" + l_debug + ", " + r_debug + ")";
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return "'" + expr.getValue() + "'";
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expr) {
        return null;
    }

    @Override
    public Object visitVariable(Variable expr) {
        return "var[" + expr.getVariable() + "]";
    }
}
