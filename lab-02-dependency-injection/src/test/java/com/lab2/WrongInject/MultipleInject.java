package com.lab2.WrongInject;

import javax.inject.Inject;

public class MultipleInject {
    @Inject
    public MultipleInject(int number1, int number2){}

    @Inject
    public MultipleInject(int number){}

    public MultipleInject(int number1, int number2, int number3){}
}
