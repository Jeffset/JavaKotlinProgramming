package com.expression;

import org.junit.Assert;
import org.junit.Test;
import java.util.Scanner;

public class ExpressionTests {
    @Test
    public void ExpressionCountTest() throws Parser.ExpressionParseException {
        Parser parser = new ParserImpl();
        Expression exp = parser.parseExpression("(3+13)*4-8+15*7");
        Assert.assertEquals((3+13)*4-8+15*7, (int) exp.compute());
        exp = parser.parseExpression("3+100*(2)*9-5/5");
        Assert.assertEquals(3+100*(2)*9-1, (int) exp.compute());
        exp = parser.parseExpression("(1+4)*(500-4)");
        Assert.assertEquals((1+4)*(500-4), (int) exp.compute());
        exp = parser.parseExpression("(4*((1-4)*(8-3))*17)-(5+2)");
        Assert.assertEquals((4*((1-4)*(8-3))*17)-(5+2), (int) exp.compute());
    }

    @Test
    public void DebugRepresentation() throws Parser.ExpressionParseException {
        Parser parser = new ParserImpl();
        String input = "(4*((1-4)*(8-3))*17)-(5+2)";
        Expression exp = parser.parseExpression(input);
        System.out.println("Binary tree for expression \'" + input + "\':");
        System.out.println(exp.debugRepresentation());
        input = "5*(99+1)/10*(3+4)";
        exp = parser.parseExpression(input);
        System.out.println("Binary tree for expression \'" + input + "\':");
        System.out.println(exp.debugRepresentation());
        input = "33/(3*7)+22-(2)";
        exp = parser.parseExpression(input);
        System.out.println("Binary tree for expression \'" + input + "\':");
        System.out.println(exp.debugRepresentation());
    }

    // Was tested in main later.
    public void VariablesNaming() throws Parser.ExpressionParseException {
        Parser parser = new ParserImpl();
        String input = "(4*((1-x)*(8-x))*17)-(y+2)";
        Expression exp = parser.parseExpression(input);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Duplicate values of \'x\' and \'y\' above:");
        double x = scanner.nextDouble();
        double y = scanner.nextDouble();
        double res = (4*((1-x)*(8-x))*17)-(y+2);
        Assert.assertEquals(String.valueOf(res), String.valueOf(exp.compute()));
    }
}
