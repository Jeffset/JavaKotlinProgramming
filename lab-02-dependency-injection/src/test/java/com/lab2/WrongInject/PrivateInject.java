package com.lab2.WrongInject;

import javax.inject.Inject;

public class PrivateInject {
    @Inject
    private PrivateInject(){}

    public PrivateInject(int number){}
}
