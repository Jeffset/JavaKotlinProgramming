public interface ExpressionVisitor {
    Object visitBinaryExpression(BinaryExpression expr);
    Object visitLiteral(Literal expr);
    Object visitVariable(VariableExpression expr);
}
