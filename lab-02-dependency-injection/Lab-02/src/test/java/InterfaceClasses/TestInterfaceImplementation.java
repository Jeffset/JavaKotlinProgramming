package InterfaceClasses;

import javax.inject.Inject;

public class TestInterfaceImplementation implements TestInterface{
    @Inject
    public TestInterfaceImplementation() {}
    @Override
    public String get() {
        return "helo";
    }
}
