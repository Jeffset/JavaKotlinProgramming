public class ComputeExpressionVisitor implements ExpressionVisitor {
    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        BinOpKind opKind = expr.getOperation();
        double left_operand = (double) expr.getLeft().accept(this);
        double right_operand = (double) expr.getRight().accept(this);
        switch (opKind) {
            case plus:
                return left_operand + right_operand;
            case minus:
                return left_operand - right_operand;
            case mult:
                return left_operand * right_operand;
            case div:
                return left_operand / right_operand;
            default:
                throw new IllegalStateException("Unexpected value: " + opKind);
        }
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return expr.getValue();
    }
}
