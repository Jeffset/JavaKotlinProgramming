package com.expression;

public class BinaryExpressionImpl implements BinaryExpression {
    /**
     * Class for working with binary operations +, -, * and /.
     * Stores the Operation variable.
     */
    private Operation operation_;
    private enum Operation {
        PLUS, MINUS, MULTIPLYING, DIVISION;
    }

    BinaryExpressionImpl(String operation) {
        switch (operation) {
            case "-" -> operation_ = Operation.MINUS;
            case "+" -> operation_ = Operation.PLUS;
            case "*" -> operation_ = Operation.MULTIPLYING;
            case "/" -> operation_ = Operation.DIVISION;
        }
    }

    @Override
    public double compute(UniqueOperator left, UniqueOperator right) {
        if (left instanceof Literal && right instanceof Literal) {
            switch (operation_.name()) {
                case "MINUS":
                    return ((Literal) left).getValue() - ((Literal) right).getValue();
                case "PLUS":
                    return ((Literal) left).getValue() + ((Literal) right).getValue();
                case "MULTIPLYING":
                    return ((Literal) left).getValue() * ((Literal) right).getValue();
                case "DIVISION":
                    return ((Literal) left).getValue() / ((Literal) right).getValue();
            }
        }
        return 0;
    }

    @Override
    public double getValue() {
        return 0;
    }
    @Override
    public void setValue() {}

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitBinaryExpression(this);
    }

    @Override
    public String toString() {
        return switch (operation_.name()) {
            case "MINUS" -> "-";
            case "PLUS" -> "+";
            case "MULTIPLYING" -> "*";
            case "DIVISION" -> "/";
            default -> " ";
        };
    }
}
