public class DebugRepresentationExpressionVisitor implements ExpressionVisitor {
    @Override
    public String visitBinaryExpression(BinaryExpression expr) {
        var leftValue = (String)expr.getLeft().accept(this);
        var rightValue = (String)expr.getRight().accept(this);
        return expr.getOperation().getOpName() + "(" + leftValue + ", " + rightValue + ")";
    }

    @Override
    public String visitLiteral(Literal expr) {
        return "CON[" + expr.getValue() + "]";
    }

    @Override
    public String visitVariable(Variable expr) {
        return "VAR[" + expr.getName() + "]";
    }
}
