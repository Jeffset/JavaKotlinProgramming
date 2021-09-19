package com._30something.expr_calc;

public class VariableImpl implements Variable {

    public VariableImpl(String name) {
        this.name = name;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitVariable(this);
    }

    @Override
    public String getName() {
        return name;
    }

    private final String name;
}
