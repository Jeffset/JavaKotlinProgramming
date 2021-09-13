package com._30something.expr_calc;

public class LiteralImpl implements Literal {

    @Override
    public Object accept(ExpressionVisitor visitor) {
        visitor.visitLiteral(this);
        return null;
    }

    @Override
    public double getValue() {
        return value;
    }

    private double value;
}
