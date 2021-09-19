package com._30something.expr_calc;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RequestVisitor implements ExpressionVisitor {

    Map<String, Double> map = new HashMap<>();
    Scanner in = new Scanner(System.in);

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        expr.getLeft().accept(this);
        expr.getRight().accept(this);
        return null;
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return null;
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expr) {
        return expr.getExpr().accept(this);
    }

    @Override
    public Object visitVariable(Variable expr) {
        String varName = expr.getName();
        if (!map.containsKey(varName)) {
            System.out.printf("Enter value for '%s': ", varName);
            map.put(varName, in.nextDouble());
        }
        return null;
    }

    public Map<String, Double> getVariablesMap() {
        return map;
    }
}
