package com._30something.expr_calc;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ExpressionParseException {
        Scanner in = new Scanner(System.in);
        ParserImpl parser = new ParserImpl();
        RequestVisitor requestVisitor = new RequestVisitor();
        ComputeExpressionVisitor computeVisitor = new ComputeExpressionVisitor();
        DebugRepresentationExpressionVisitor debugVisitor = new DebugRepresentationExpressionVisitor();
        DepthVisitor depthVisitor = new DepthVisitor();
        ToStringVisitor toStringVisitor = ToStringVisitor.INSTANCE;

        System.out.print("Enter expression: ");
        Expression expr = parser.parseExpression(in.nextLine());
        System.out.print("Tree: ");
        System.out.println((String) (expr.accept(debugVisitor)));
        System.out.print("Expr-tree depth: ");
        System.out.println(expr.accept(depthVisitor));
        System.out.print("Reconstructed expression: ");
        System.out.println((String) (expr.accept(toStringVisitor)));

        System.out.print("Result: ");
        System.out.println(expr.accept(computeVisitor));
    }
}
