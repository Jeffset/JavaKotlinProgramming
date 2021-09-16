package com._30something.expr_calc;

public class LiteralImpl implements Literal {

    public LiteralImpl(String stringValue, ParserImpl.CharTypes valueType) {
        this.stringValue = stringValue;
        this.valueType = valueType;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitLiteral(this);
    }

    @Override
    public double getValue() {
        return Double.parseDouble(stringValue);
    }

    public String getString() {
        return stringValue;
    }

    public ParserImpl.CharTypes getType() {
        return valueType;
    }

    private final String stringValue;
    private final ParserImpl.CharTypes valueType;
}
