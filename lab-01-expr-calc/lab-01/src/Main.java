import exceptions.ExpressionParseException;

import java.util.*;

public class Main {
    private static String removeWhitespaces(String input) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < input.length(); i++) {
            if(input.charAt(i) != ' ') {
                builder.append(input.charAt(i));
            }
         }
        return builder.toString();
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while(true) {
            String input = in.nextLine();
            Expression root;
            if(Objects.equals(input, "q") || Objects.equals(input, "quit")) {
                in.close();
                return;
            }
            try {
                root = new ParserImpl().parseExpression(input);
                System.out.println("Inserted Expression: " + removeWhitespaces(input));
                System.out.println("Tree: " + root.accept(new DebugRepresentationExpressionVisitor()));
                System.out.println("Tree depth: " + root.accept(new TreeDepthVisitor()));
                var variables = (Map<String, Double>) root.accept(new VariableQueryVisitor());
                double result = (Double) root.accept(new ComputeExpressionVisitor(variables));
                System.out.println("Result: " + result);
            } catch (ExpressionParseException e) {
                System.err.println("Wrong symbol found");
            } catch (EmptyStackException e) {
                System.err.println("Syntax error");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
