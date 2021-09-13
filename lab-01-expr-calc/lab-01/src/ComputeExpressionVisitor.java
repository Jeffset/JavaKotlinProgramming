public class ComputeExpressionVisitor implements ExpressionVisitor{
    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        return null;
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return null;
    }
}
