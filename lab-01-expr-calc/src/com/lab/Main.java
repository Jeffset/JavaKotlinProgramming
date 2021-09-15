package com.lab;

import java.util.*;

public class Main {
    public static void main(String[] args) throws ExpressionParseException {
        Parser parser = new ParserImpl();
        Expression expression = parser.parseExpression("3 + 1 * x * y * 5 / z");
        String debug = (String) expression.accept(new DebugRepresentationExpressionVisitor());
        int depth = (Integer) expression.accept(new DepthExpressionVisitor());
        System.out.println(debug);
        System.out.println(depth);
        HashSet<String> set = (HashSet<String>) expression.accept(new VariablesExpressionVisitor());
        HashMap<String, Double> result_map = new HashMap<>();
        for (String variable : set) {
            System.out.print("value for " + "'" + variable + "': ");
            Scanner scanner = new Scanner(System.in);
            double number = scanner.nextDouble();
            result_map.put(variable, number);
        }
        double result = (Double) expression.accept(new ComputeExpressionVisitor(result_map));
        System.out.println(result);
    }
}
