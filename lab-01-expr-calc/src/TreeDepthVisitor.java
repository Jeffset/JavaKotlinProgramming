import static java.lang.Math.max;

public class TreeDepthVisitor implements ExpressionVisitor<Integer> {
    private TreeDepthVisitor() {}

    @Override
    public Integer visitBinaryExpression(BinaryExpression expr) {
        return 1 + max(expr.getLeft().accept(this), expression.getRight().accept(this));
    }

    @Override
    public Integer visitLiteral(Literal expr) {
        return 1;
    }

    @Override
    public Integer visitVariable(Variable expr) {
        return 1;
    }

    @Override
    public Integer visitParenthesis(ParenthesisExpression expr) {
        return expr.getExpr().accept(this) + 1;
    }

    public static TreeDepthVisitor INSTANCE = new TreeDepthVisitor();
}