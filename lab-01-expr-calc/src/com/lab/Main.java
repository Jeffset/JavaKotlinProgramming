package com.lab;

import java.util.*;

public class Main {
    public static void main(String[] args) throws ExpressionParseException {
        Parser parser = new ParserImpl();

        System.out.print("enter expression: ");
        Scanner scanner = new Scanner(System.in);
        char[] str_expr = scanner.nextLine().toCharArray();
        StringBuilder right_str = new StringBuilder();
        for (char symbol : str_expr) {
            if (symbol == '(' || symbol == ')') {
                right_str.append(" ").append(symbol).append(" ");
                continue;
            }
            right_str.append(symbol);
        }

        Expression expression = parser.parseExpression(right_str.toString());
        String debug = (String) expression.accept(new DebugRepresentationExpressionVisitor());
        int depth = (Integer) expression.accept(new DepthExpressionVisitor());
        System.out.println(debug);
        System.out.println(depth);

        HashSet<String> set = (HashSet<String>) expression.accept(new VariablesExpressionVisitor());
        HashMap<String, Double> result_map = new HashMap<>();
        for (String variable : set) {
            System.out.print("value for " + "'" + variable + "': ");
            double number = scanner.nextDouble();
            result_map.put(variable, number);
        }
        double result = (Double) expression.accept(new ComputeExpressionVisitor(result_map));
        System.out.println(result);
    }
}
