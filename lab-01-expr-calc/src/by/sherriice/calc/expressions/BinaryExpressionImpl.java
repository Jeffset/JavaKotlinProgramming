package by.sherriice.calc.expressions;

import by.sherriice.calc.ExpressionVisitor;
import by.sherriice.calc.token.BinOpKind;

public class BinaryExpressionImpl implements BinaryExpression {
    Expression left_expression;
    Expression right_expression;
    String operation;

    public BinaryExpressionImpl(String operation, Expression left_expression, Expression right_expression) {
        this.left_expression = left_expression;
        this.right_expression = right_expression;
        this.operation = operation;
    }

    @Override
    public Expression getLeft() {
        return this.left_expression;
    }

    @Override
    public Expression getRight() {
        return this.right_expression;
    }

    @Override
    public String getOperation() {
        return this.operation;
    }

    @Override
    public String debugRepresentation() {
        return(operation + "( " + left_expression.debugRepresentation() + ", " + right_expression.debugRepresentation() + ")");
    }
}
