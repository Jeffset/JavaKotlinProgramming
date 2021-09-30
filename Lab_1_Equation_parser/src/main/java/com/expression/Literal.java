package com.expression;
import java.util.Scanner;

public interface Literal extends UniqueOperator {
    void setValue();
    double getValue();
    String toString();
}

