import java.util.HashMap;
import java.util.Objects;

public class MainTest {
    static double calculate(String input) {
        Expression root;
        try {
            root = new ParserImpl().parseExpression(input);
            return (double) root.accept(new ComputeExpressionVisitor(new HashMap<>()));
        } catch (Exception e) {
            System.out.println("Something went wrong in tests. Please try again");
        }
        return 0.0;
    }

    static void expectEq(double first, double second) {
        if (!Objects.equals(first, second)) {
            System.err.println("Error: Expected value: " + first + ", Got Value: " + second);
        }
    }

    public static void main(String[] args) {
        expectEq(7, calculate("2+5"));
        expectEq(2, calculate("2"));
        expectEq(7, calculate("(2+5)"));
        expectEq(14, calculate("(2+5)*2"));
        expectEq(1, calculate("(       2       +       3         )         /         5"));
        expectEq(70, calculate("(4+3)*(9+1)"));
    }
}
