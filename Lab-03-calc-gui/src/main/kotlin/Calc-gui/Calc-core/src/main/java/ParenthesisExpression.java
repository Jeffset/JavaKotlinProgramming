public class ParenthesisExpression implements Expression {
    final public BraceType brace_type;

    public ParenthesisExpression(BraceType brace_type) {
        this.brace_type = brace_type;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return null;
    }
}
