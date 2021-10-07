package com.lab2;

import com.lab2.SumsOnTrees.*;
import com.lab2.WrongInject.*;
import org.junit.Test;

public class ExceptionTests {
    @Test(expected = DiFrameworkException.class)
    public void MultipleInjectTest() {
        DiFramework myDi = new DiFrameworkImpl();
        myDi.register(MainClass.class);
        myDi.register(MultipleInject.class);
        myDi.completeRegistration();
    }

    @Test(expected = DiFrameworkException.class)
    public void PrivateInjectTest() {
        DiFramework myDi = new DiFrameworkImpl();
        myDi.register(MainClass.class);
        myDi.register(PrivateInject.class);
        myDi.completeRegistration();
    }

    @Test(expected = DiFrameworkException.class)
    public void WithoutInjectTest() {
        DiFramework myDi = new DiFrameworkImpl();
        myDi.register(MainClass.class);
        myDi.register(WithoutInject.class);
        myDi.completeRegistration();
    }

    @Test(expected = DiFrameworkException.class)
    public void WrongRegistrationTest1() {
        DiFramework myDi = new DiFrameworkImpl();
        myDi.register(Vertex1.class);
        myDi.register(Vertex2.class);
        myDi.register(Vertex3.class);
        myDi.register(Vertex4.class);
        myDi.register(Vertex5.class, Vertex5Impl1.class);
        myDi.completeRegistration();
    }

    @Test(expected = DiFrameworkException.class)
    public void WrongRegistrationTest2() {
        DiFramework myDi = new DiFrameworkImpl();
        myDi.register(Vertex1.class);
        myDi.register(Vertex2.class);
        myDi.register(Vertex3.class, Vertex3Impl1.class);
        myDi.register(Vertex3.class, Vertex3Impl2.class);
        myDi.register(Vertex4.class);
        myDi.register(Vertex5.class, Vertex5Impl1.class);
        myDi.completeRegistration();
    }

    @Test(expected = DiFrameworkException.class)
    public void WrongRegistrationTest3() {
        DiFramework myDi = new DiFrameworkImpl();
        myDi.register(Vertex1.class);
        myDi.register(Vertex2.class);
        myDi.register(Vertex4.class);
        myDi.register(Vertex5.class, Vertex5Impl1.class);
        myDi.completeRegistration();
        myDi.register(Vertex3.class, Vertex3Impl1.class);
    }

    @Test(expected = DiFrameworkException.class)
    public void WrongRegistrationTest4() {
        DiFramework myDi = new DiFrameworkImpl();
        myDi.register(Vertex1.class);
        myDi.register(Vertex2.class);
        myDi.register(Vertex3Impl2.class, Vertex3Impl1.class);
        myDi.register(Vertex4.class);
        myDi.register(Vertex5.class, Vertex5Impl1.class);
        myDi.completeRegistration();
    }

    @Test(expected = DiFrameworkException.class)
    public void WrongRegistrationTest5() {
        DiFramework myDi = new DiFrameworkImpl();
        myDi.register(Vertex1.class);
        myDi.register(Vertex2.class);
        myDi.register(Vertex2.class);
        myDi.register(Vertex3.class, Vertex3Impl1.class);
        myDi.register(Vertex4.class);
        myDi.register(Vertex5.class, Vertex5Impl1.class);
        myDi.completeRegistration();
    }
}
