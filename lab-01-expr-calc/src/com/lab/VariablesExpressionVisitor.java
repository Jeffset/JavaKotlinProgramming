package com.lab;

import java.util.HashSet;
import java.util.Set;

public class VariablesExpressionVisitor implements ExpressionVisitor {

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        HashSet<String> l_set = (HashSet<String>) expr.getLeft().accept(new VariablesExpressionVisitor());
        HashSet<String> r_set = (HashSet<String>) expr.getRight().accept(new VariablesExpressionVisitor());
        l_set.addAll(r_set);
        return l_set;
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return new HashSet<>();
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expr) {
        return null;
    }

    @Override
    public Object visitVariable(Variable expr) {
        Set<String> set = new HashSet<>();
        set.add(expr.getVariable());
        return set;
    }
}
