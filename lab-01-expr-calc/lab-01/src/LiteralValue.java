public class LiteralValue implements Literal {

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return null;
    }

    public LiteralValue(int value) {
        this.value = value;
    }

    int value;
}
