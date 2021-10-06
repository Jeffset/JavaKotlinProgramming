package com.lab01;

import java.util.Scanner;

public class LiteralImpl implements Literal {
    private String mName = "";
    private double mValue;
    private boolean mIsValueSet = false;

    public LiteralImpl(String name) {
        this.mName = name;
    }

    public LiteralImpl(double value) {
        this.mValue = value;
        mIsValueSet = true;
    }

    @Override
    public double compute() {
        return getValue();
    }

    @Override
    public String debugRepresentation() {
        return mName.isEmpty() ?
                String.format(mValue == (long) mValue ? "'%.0f'": "'%s'", mValue) : String.format("[%s]", mName);
    }

    @Override
    public int depth() {
        return 1;
    }

    @Override
    public <ResultType> ResultType accept(ExpressionVisitor<ResultType> visitor) {
        return visitor.visitLiteral(this);
    }

    @Override
    public double getValue() {
        if (!mIsValueSet) {
            System.out.printf("Enter value of '%s': ", mName);
            mValue = new Scanner(System.in).nextDouble();
            mIsValueSet = true;
        }
        return mValue;
    }

    @Override
    public String getName() {
        return mName;
    }
}
