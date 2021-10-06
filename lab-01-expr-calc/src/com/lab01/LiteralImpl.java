package com.lab01;

import java.util.Scanner;
import java.util.function.Function;

public class LiteralImpl implements Literal {
    private String name = "";
    private double value;
    private boolean isValueSet = false;

    public LiteralImpl(String name) {
        this.name = name;
    }

    public LiteralImpl(double value) {
        this.value = value;
        isValueSet = true;
    }

    @Override
    public double compute() {
        return getValue();
    }

    @Override
    public String debugRepresentation() {
        return name.isEmpty() ?
                String.format(value == (long) value ? "'%.0f'": "'%s'", value) : String.format("[%s]", name);
    }

    @Override
    public int depth() {
        return 1;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return null;
    }

    @Override
    public double getValue() {
        if (!isValueSet) {
            System.out.printf("Enter value of '%s': ", name);
            value = new Scanner(System.in).nextDouble();
            isValueSet = true;
        }
        return value;
    }
}
