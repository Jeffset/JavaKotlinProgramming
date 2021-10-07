package com.lab2.WrongInject;

import javax.inject.Inject;

public class MainClass {

    @Inject
    public MainClass(Object wrong_class) {}
}
