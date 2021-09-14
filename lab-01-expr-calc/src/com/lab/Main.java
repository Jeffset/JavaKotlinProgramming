package com.lab;

public class Main {
    public static void main(String[] args) throws ExpressionParseException {
        Parser parser = new ParserImpl();
        Expression expression = parser.parseExpression("3 + 4 * 2 / 4");
        double result = (Double) expression.accept(new ComputeExpressionVisitor());
        String debug = (String) expression.accept(new DebugRepresentationExpressionVisitor());
        System.out.println(result);
        System.out.println(debug);
    }
}
