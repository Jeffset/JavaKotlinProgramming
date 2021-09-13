public class BinaryExpressionImpl implements BinaryExpression{

    public BinaryExpressionImpl(BinOpKind bin_op_kind, Expression expression_left, Expression expression_right) {
        this.bin_op_kind = bin_op_kind;
        this.expression_right = expression_right;
        this.expression_left = expression_left;
    }

    @Override
    public Expression getLeft() {
        return expression_left;
    }

    @Override
    public Expression getRight() {
        return expression_right;
    }

    @Override
    public BinOpKind getOperation() {
        return bin_op_kind;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return null;
    }
    BinOpKind bin_op_kind;
    Expression expression_right;
    Expression expression_left;
}
