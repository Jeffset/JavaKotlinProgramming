import exceptions.ExpressionParseException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = in.next();
        in.close();
        Expression root;
        try {
            root = new ParserImpl().parseExpression(input);
            System.out.println("tree: " + root.accept(new DebugRepresentationExpressionVisitor()));
            double result = (Double) root.accept(new ComputeExpressionVisitor());
            System.out.println("result: " + result);
        }
        catch (ExpressionParseException e) {
            System.out.println("something went wrong in parser");
            e.printStackTrace();
        }
    }
}
