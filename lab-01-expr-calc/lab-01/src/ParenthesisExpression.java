public class ParenthesisExpression implements Expression {
    public ParenthesisExpression(BraceType brace_type) {
        this.brace_type = brace_type;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return null;
    }
    public BraceType brace_type;
}
