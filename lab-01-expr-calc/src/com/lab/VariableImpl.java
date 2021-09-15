package com.lab;

public class VariableImpl implements Variable {

    VariableImpl(String string) {
        string_ = string;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitVariable(this);
    }

    @Override
    public String getVariable() {
        return string_;
    }

    private final String string_;
}
