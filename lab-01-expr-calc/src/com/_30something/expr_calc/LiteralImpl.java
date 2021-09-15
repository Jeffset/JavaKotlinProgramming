package com._30something.expr_calc;

public class LiteralImpl implements Literal {

    public LiteralImpl(String string_value, ParserImpl.CharTypes value_type) {
        this.string_value = string_value;
        this.value_type = value_type;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitLiteral(this);
    }

    @Override
    public double getValue() {
        return Double.parseDouble(string_value);
    }

    public String getString() {
        return string_value;
    }

    public ParserImpl.CharTypes getType() {
        return value_type;
    }

    private final String string_value;
    private final ParserImpl.CharTypes value_type;
}
