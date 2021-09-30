package com.expression;

import java.util.Scanner;

public class LiteralImpl implements Literal {
    /**
     * Class for working with variables and constants in equation.
     */
    private double value_;
    private final String name_;

    LiteralImpl(String name, double value) {
        name_ = name;
        value_ = value;
    }

    LiteralImpl(String name) {
        name_ = name;
    }

    LiteralImpl(double value) {
        name_ = "const";
        value_ = value;
    }

    @Override
    public double getValue() {
        return value_;
    }

    @Override
    public String toString() {
        if (name_.equals("const")) {
            return String.valueOf(value_);
        } else {
            return name_;
        }
    }

    @Override
    public double compute(UniqueOperator left, UniqueOperator right) {
        return value_;
    }

    /**
     * this function sets the numerical value of the variable 'value_', which will be entered by the user.
     */
    @Override
    public void setValue() {
        System.out.println("Set value for the variable " + name_ + ":");
        Scanner c = new Scanner(System.in);
        value_ = c.nextDouble();
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitLiteral(this);
    }
}
