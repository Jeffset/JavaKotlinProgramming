import exceptions.ExpressionParseException;

import java.util.EmptyStackException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static String toString(String input) {
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
                return;
            }
            try {
                root = new ParserImpl().parseExpression(input);
                System.out.println("Inserted Expression: " + toString(input));
                System.out.println("Tree: " + root.accept(new DebugRepresentationExpressionVisitor()));
                System.out.println("Tree depth: " + root.accept(new TreeDepthVisitor()));
                double result = (Double) root.accept(new ComputeExpressionVisitor());
                System.out.println("Result: " + result);
            } catch (ExpressionParseException e) {
                System.out.println("Wrong symbol found");
            } catch (EmptyStackException e) {
                System.out.println("Syntax error");
            } catch (Exception e) {
                System.out.println("Something went wrong. Please try again");
            }
        }
//        in.close();
    }
}
