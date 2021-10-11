package com._30something.expr_calc;

public class LiteralImpl implements Literal {

    private final Double value;

    public LiteralImpl(Double value) {
        this.value = value;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitLiteral(this);
    }

    @Override
    public double getValue() {
        return value;
    }
}
