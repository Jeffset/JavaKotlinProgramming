public class DebugRepresentationExpressionVisitor implements ExpressionVisitor {

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        String left_result = (String) expr.getLeft().accept(this);
        String right_result = (String) expr.getRight().accept(this);
        String our_result = "?";
        switch (expr.getOperation()) {
            case plus:
                our_result = "add(";
                break;
            case minus:
                our_result = "sub(";
                break;
            case mult:
                our_result = "mult(";
                break;
            case div:
                our_result = "div(";
                break;
        }
        return our_result + left_result + ", " + right_result + ")";
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return new String(String.valueOf(expr.getValue()));
    }
}
