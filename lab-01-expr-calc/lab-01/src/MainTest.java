import exceptions.ExpressionParseException;

import java.util.EmptyStackException;
import java.util.Objects;

public class MainTest {
    static double Calculate(String input) {
        Expression root;
        try {
            root = new ParserImpl().parseExpression(input);
            return (double) root.accept(new ComputeExpressionVisitor());
        } catch (Exception | ExpressionParseException e) {
            System.out.println("Something went wrong in tests. Please try again");
        }
        return 0.0;
    }

    static void EXPECT_EQ(double first, double second) {
        if (!Objects.equals(first, second)) {
            System.err.println("Error: Expected value: " + first + ", Got Value: " + second);
        }
    }

    public static void main(String[] args) {
        EXPECT_EQ(7, Calculate("2+5"));
        EXPECT_EQ(2, Calculate("2"));
        EXPECT_EQ(7, Calculate("(2+5)"));
        EXPECT_EQ(14, Calculate("(2+5)*2"));
        EXPECT_EQ(1, Calculate("(       2       +       3         )         /         5"));
        EXPECT_EQ(70, Calculate("(4+3)*(9+1)"));
    }
}
