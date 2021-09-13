package com._30something.expr_calc;

public class Main {
    public static void main(String[] args) {
        Parser parser = new ParserImpl();
        try {
            Expression expr = parser.parseExpression("(22 + 5) * 74 - 3");
            //System.out.println(expr.debugRepresentation());
        } catch (ExpressionParseException exception) {
            System.out.println("hahaha");
        }
    }
}
