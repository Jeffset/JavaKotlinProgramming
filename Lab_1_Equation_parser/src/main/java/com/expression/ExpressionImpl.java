package com.expression;


public class ExpressionImpl implements Expression {
    /**
     * An instance of this class is a node of the binary expression tree that stores the UniqueOperator,
     * as well as references to the parent expression and two descendants.
     */
    private Expression parent_;
    private Expression left_;
    private Expression right_;
    private final UniqueOperator value_;

    ExpressionImpl(Expression left, Expression right, Expression parent, UniqueOperator u) {
        parent_ = parent;
        right_ = right;
        left_ = left;
        value_ = u;
    }

    /**
     * Using ExpressionTreeBuilder, which implements ExpressionVisitor, creates a string with beautiful binary tree.
     * @return String with Binary Tree presentation.
     */
    @Override
    public String debugRepresentation() {
        StringBuffer buffer = new StringBuffer(100);
        ExpressionTreeBuilder builder = new ExpressionTreeBuilder(buffer);
        return String.valueOf(this.accept(builder));
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return (visitor instanceof ExpressionTreeBuilder) ? ((ExpressionTreeBuilder) visitor).visitExpression(this) : null;
    }

    /**
     * @return Result of the given expression.
     */
    @Override
    public double compute() {
         if (this.getLeft() == null && this.getRight() == null) {
             return this.getValue().getValue();
         } else if (this.getLeft().getValue() instanceof Literal && this.getRight().getValue() instanceof Literal) {
            return this.getValue().compute(this.getLeft().getValue(), this.getRight().getValue());
        } else {
            return this.getValue().compute(new LiteralImpl(this.getLeft().compute()),
                                           new LiteralImpl(this.getRight().compute()));
        }
    }

    @Override
    public Expression getLeft() {
        return left_;
    }

    @Override
    public Expression getRight() {
        return right_;
    }

    @Override
    public UniqueOperator getValue() {
        return value_;
    }

    public void setLeft(Expression left) {
        left_ = left;
    }
    public void setRight(Expression right) {
        right_ = right;
    }
    public void setParent(Expression parent) {
        parent_ = parent;
    }
}
