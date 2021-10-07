package com.lab2;

import com.lab2.SumsOnTrees.*;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class BasicTests {
    @Test
    public void Test1() throws DiFrameworkException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        DiFramework myDi = new DiFrameworkImpl();
        myDi.register(Vertex1.class);
        myDi.register(Vertex2.class);
        myDi.register(Vertex3.class, Vertex3Impl1.class);
        myDi.register(Vertex4.class);
        myDi.register(Vertex5.class, Vertex5Impl1.class);
        myDi.completeRegistration();

        Vertex1 vertex1 = (Vertex1) myDi.resolve(Vertex1.class);
        Assert.assertEquals(vertex1.GetSubtreeSum(), 25);

        Vertex2 vertex2 = (Vertex2) myDi.resolve(Vertex2.class);
        Assert.assertEquals(vertex2.GetSubtreeSum(), 15);

        Vertex3 vertex3 = (Vertex3) myDi.resolve(Vertex3.class);
        Assert.assertEquals(vertex3.GetSubtreeSum(), 5);

        Vertex4 vertex4 = (Vertex4) myDi.resolve(Vertex4.class);
        Assert.assertEquals(vertex4.GetSubtreeSum(), 5);

        Vertex5 vertex5 = (Vertex5) myDi.resolve(Vertex5.class);
        Assert.assertEquals(vertex5.GetSubtreeSum(), 5);
    }

    @Test
    public void Test2() throws DiFrameworkException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        DiFramework myDi = new DiFrameworkImpl();
        myDi.register(Vertex1.class);
        myDi.register(Vertex2.class);
        myDi.register(Vertex3.class, Vertex3Impl2.class);
        myDi.register(Vertex4.class);
        myDi.register(Vertex5.class, Vertex5Impl1.class);
        myDi.completeRegistration();

        Vertex1 vertex1 = (Vertex1) myDi.resolve(Vertex1.class);
        Assert.assertEquals(vertex1.GetSubtreeSum(), 20);

        Vertex2 vertex2 = (Vertex2) myDi.resolve(Vertex2.class);
        Assert.assertEquals(vertex2.GetSubtreeSum(), 15);

        Vertex3 vertex3 = (Vertex3) myDi.resolve(Vertex3.class);
        Assert.assertEquals(vertex3.GetSubtreeSum(), 0);

        Vertex4 vertex4 = (Vertex4) myDi.resolve(Vertex4.class);
        Assert.assertEquals(vertex4.GetSubtreeSum(), 5);

        Vertex5 vertex5 = (Vertex5) myDi.resolve(Vertex5.class);
        Assert.assertEquals(vertex5.GetSubtreeSum(), 5);
    }

    @Test
    public void Test3() throws DiFrameworkException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        DiFramework myDi = new DiFrameworkImpl();
        myDi.register(Vertex1.class);
        myDi.register(Vertex2.class);
        myDi.register(Vertex3.class, Vertex3Impl2.class);
        myDi.register(Vertex4.class);
        myDi.register(Vertex5.class, Vertex5Impl2.class);
        myDi.completeRegistration();

        Vertex1 vertex1 = (Vertex1) myDi.resolve(Vertex1.class);
        Assert.assertEquals(vertex1.GetSubtreeSum(), 15);

        Vertex2 vertex2 = (Vertex2) myDi.resolve(Vertex2.class);
        Assert.assertEquals(vertex2.GetSubtreeSum(), 10);

        Vertex3 vertex3 = (Vertex3) myDi.resolve(Vertex3.class);
        Assert.assertEquals(vertex3.GetSubtreeSum(), 0);

        Vertex4 vertex4 = (Vertex4) myDi.resolve(Vertex4.class);
        Assert.assertEquals(vertex4.GetSubtreeSum(), 5);

        Vertex5 vertex5 = (Vertex5) myDi.resolve(Vertex5.class);
        Assert.assertEquals(vertex5.GetSubtreeSum(), 0);
    }
}
