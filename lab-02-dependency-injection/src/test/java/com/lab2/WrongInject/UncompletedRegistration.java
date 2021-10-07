package com.lab2.WrongInject;

import javax.inject.Inject;

public class UncompletedRegistration {
    @Inject
    public UncompletedRegistration(OtherClass other_class){}
}

class OtherClass {
    @Inject
    public OtherClass(){}
}
