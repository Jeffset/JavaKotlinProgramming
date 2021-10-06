package com.lab01;

public class TestMain {
    private static final double mEps = 1e-6;

    private static boolean IsSame(double first, double second) {
        return Math.abs(first - second) < mEps;
    }

    public static void main(String[] args) throws ExpressionParseException {
        ParserImpl parser = new ParserImpl();

        // Test 1
        {
            Expression expr = parser.parseExpression("2 + 2");

            assert (IsSame(expr.compute(), 4));
            assert IsSame(expr.accept(new ComputeExpressionVisitor()), 4);

            assert expr.depth() == 2;
            assert expr.accept(new DepthExpressionVisitor()) == 2;

            assert expr.debugRepresentation().equals("Plus('2', '2')");
            assert expr.accept(new DebugRepresentationExpressionVisitor()).equals("Plus('2', '2')");
        }

        // Test 2
        {
            Expression expr = parser.parseExpression("1337");

            assert (IsSame(expr.compute(), 1337));
            assert IsSame(expr.accept(new ComputeExpressionVisitor()), 1337);

            assert expr.depth() == 1;
            assert expr.accept(new DepthExpressionVisitor()) == 1;

            assert expr.debugRepresentation().equals("'1337'");
            assert expr.accept(new DebugRepresentationExpressionVisitor()).equals("'1337'");
        }

        // Test 3
        {
            try {
                Expression expr = parser.parseExpression("");
                assert false;  // We except previous line to throw
            } catch (ExpressionParseException e) {
                assert e.getMessage().equals("Empty expression");
            }
        }
    }
}
