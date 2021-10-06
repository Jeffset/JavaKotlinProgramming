package com.lab01;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("Enter expression: ");
        try {
            Expression expression = new ParserImpl().parseExpression(new Scanner(System.in).nextLine());
            System.out.println("Tree: " + expression.debugRepresentation());
            System.out.println("Depth: " + expression.depth());
            System.out.println("Result: " + expression.compute());
        } catch (Exception parseException) {
            System.out.println(parseException.getMessage());
        }
    }
}
