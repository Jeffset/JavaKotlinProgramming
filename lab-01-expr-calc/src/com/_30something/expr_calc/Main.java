package com._30something.expr_calc;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ExpressionParseException {
        ParserImpl parser = new ParserImpl();
        System.out.print("Enter expression: ");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        List<ParserImpl.Token> list = parser.buildPolishNotation(parser.verifyTokens(parser.tokenize(input)));
        for (ParserImpl.Token token : list) {
            System.out.print(token.string);
            System.out.print(' ');
            System.out.println(token.type);
        }
        Expression expr = parser.buildExpression(list);
        DebugRepresentationExpressionVisitor debug_visitor = new DebugRepresentationExpressionVisitor();
        System.out.println((String)(debug_visitor.visitBinaryExpression((BinaryExpression)expr)));
        //System.out.println(parser.parseExpression(input));
    }
}
