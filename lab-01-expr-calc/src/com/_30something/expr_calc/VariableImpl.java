package com._30something.expr_calc;

public class VariableImpl implements Variable {

    private final String name;

    public VariableImpl(String name) {
        this.name = name;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitVariable(this);
    }

    @Override
    public String getName() {
        return name;
    }
}
