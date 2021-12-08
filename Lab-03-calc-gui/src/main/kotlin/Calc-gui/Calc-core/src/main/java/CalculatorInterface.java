import exceptions.ExpressionParseException;

import java.util.EmptyStackException;
import java.util.Map;

public class CalculatorInterface {
    static public double Calculate(String input) throws ExpressionParseException, EmptyStackException, Exception {
        Expression root;
        root = new ParserImpl().parseExpression(input);
        System.out.println("Tree: " + root.accept(new DebugRepresentationExpressionVisitor()));
        System.out.println("Tree depth: " + root.accept(new TreeDepthVisitor()));
        var variables = (Map<String, Double>) root.accept(new VariableQueryVisitor());
        return (Double) root.accept(new ComputeExpressionVisitor(variables));
    }
}
