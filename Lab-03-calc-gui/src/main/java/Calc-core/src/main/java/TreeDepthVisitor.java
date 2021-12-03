public class TreeDepthVisitor implements ExpressionVisitor{
    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        int left = (int) expr.getLeft().accept(this);
        int right = (int) expr.getRight().accept(this);
        if(left > right) {
            return left + 1;
        } else {
            return right + 1;
        }
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return 1;
    }

    @Override
    public Object visitVariable(VariableExpression expr) {
        return 1;
    }
}
