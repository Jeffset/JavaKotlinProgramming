package com.expression;

public class ExpressionTreeBuilder implements ExpressionVisitor {
    /**
     * Class that constructs a picture of Binary Tree with given root.
     */
    private int current_pose;
    private StringBuffer buffer_;
    private String prefix;
    private String childrenPrefix;

    ExpressionTreeBuilder(StringBuffer buffer) {
        buffer_ = buffer;
        prefix = "";
        childrenPrefix = "";
    }

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        return "[" + expr.toString() + "]";
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return expr.toString();
    }

    @Override
    public Object visitExpression(Expression exp) {
        return printTree(exp);
    }

    public String printTree(Expression exp) {
        this.print(exp, "", "");
        return buffer_.toString();
    }

    private void print(Expression expression, String prefix_new, String childrenPrefix_new) {
        prefix = prefix_new;
        buffer_.append(prefix);
        buffer_.append(expression.getValue().accept(this));
        buffer_.append('\n');
        if (expression.getValue() instanceof BinaryExpression) {
            this.print(expression.getLeft(), childrenPrefix_new  + "L__ ",
                    childrenPrefix_new + "|   ");
            this.print(expression.getRight(), childrenPrefix_new + "L__ ",
                    childrenPrefix_new + "    ");
        }
    }

}
