import exceptions.ExpressionParseException;

import java.util.EmptyStackException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        Expression root;
        try {
            root = new ParserImpl().parseExpression(input);
            System.out.println("tree: " + root.accept(new DebugRepresentationExpressionVisitor()));
            System.out.println("tree depth: " + root.accept(new TreeDepthVisitor()));
            double result = (Double) root.accept(new ComputeExpressionVisitor());
            System.out.println("result: " + result);
        } catch (ExpressionParseException e) {
            System.out.println("Wrong symbol found");
        } catch (EmptyStackException e) {
            System.out.println("Syntax error");
        }
        in.close();
    }
}
