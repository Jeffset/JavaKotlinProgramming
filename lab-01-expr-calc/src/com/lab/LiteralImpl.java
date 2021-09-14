package com.lab;

public class LiteralImpl implements Literal {

    LiteralImpl(double value) {
        value_ = value;
    }

    @Override
    public double getValue() {
        return value_;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitLiteral(this);
    }

    private final double value_;
}
