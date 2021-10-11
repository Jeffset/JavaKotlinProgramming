package com._30something.expr_calc;

/**
 * Visitor class used to count the depth of expression tree.
 * <strong>In fact counts the distance from current vertex to the farthest leaf plus 1</strong>.
 * This distance for root matches with the depth of tree.
 *
 * @author 30something
 * @version 1.0
 */

public class DepthVisitor implements ExpressionVisitor<Integer> {

    @Override
    public Integer visitBinaryExpression(BinaryExpression expr) {
        Integer leftRes = expr.getLeft().accept(this);
        Integer rightRes = expr.getRight().accept(this);
        return Math.max(leftRes, rightRes) + 1;
    }

    @Override
    public Integer visitLiteral(Literal expr) {
        return 1;
    }

    @Override
    public Integer visitParenthesis(ParenthesisExpression expr) {
        return expr.getExpr().accept(this) + 1;
    }

    @Override
    public Integer visitVariable(Variable expr) {
        return 1;
    }
}
