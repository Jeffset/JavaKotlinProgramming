package com.lab;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = null;
        Expression expression = null;
        boolean except = true;
        while (except) {
            except = false;
            try {
                Parser parser = new ParserImpl();
                System.out.print("enter expression: ");
                scanner = new Scanner(System.in);
                char[] str_expr = scanner.nextLine().toCharArray();
                StringBuilder right_str = new StringBuilder();
                for (char symbol : str_expr) {
                    if (symbol == '(' || symbol == ')') {
                        right_str.append(' ').append(symbol).append(' ');
                        continue;
                    }
                    right_str.append(symbol);
                }
                expression = parser.parseExpression(right_str.toString());
            } catch (ExpressionParseException exception) {
                except = true;
                System.out.println("You have " + exception.getMessage() + ", " +
                        "please try again with correct expression.");
            }
        }
        String debug = (String) expression.accept(DebugRepresentationExpressionVisitor.INSTANCE);
        int depth = (Integer) expression.accept(DepthExpressionVisitor.INSTANCE);
        System.out.println("tree: " + debug);
        System.out.println("expr-tree depth: " + depth);

        HashSet<String> set = (HashSet<String>) expression.accept(new VariablesExpressionVisitor());
        HashMap<String, Double> result_map = new HashMap<>();
        for (String variable : set) {
            System.out.print("value for " + "'" + variable + "': ");
            double number = scanner.nextDouble();
            result_map.put(variable, number);
        }
        double result = (Double) expression.accept(new ComputeExpressionVisitor(result_map));
        System.out.println("result: " + result);
        String to_string = (String) expression.accept(ToStringVisitor.INSTANCE);
        System.out.println(to_string);
    }
}
