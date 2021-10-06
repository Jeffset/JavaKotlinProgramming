package com.lab2;

import java.lang.reflect.InvocationTargetException;

public interface DiFramework {
    Object resolve(Class<?> some_class) throws NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException;
    void register(Class<?> some_class) throws DiFrameworkException;
    void register(Class<?> some_interface, Class<?> implementation) throws DiFrameworkException;
    void completeRegistration();
}
