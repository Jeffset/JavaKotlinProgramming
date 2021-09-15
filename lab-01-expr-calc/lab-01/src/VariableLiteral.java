public class VariableLiteral implements Literal {
    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitVariable(this);
    }

    public String getName() {
        return name;
    }

    public VariableLiteral(String name) {
        this.name = name;
    }

    private final String name;

    @Override
    public double getValue() {
        return 0;
    }
}
