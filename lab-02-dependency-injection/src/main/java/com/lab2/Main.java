package com.lab2;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface AnotherClass {
    int getNumber();
}

class AnotherClass1 implements AnotherClass {

    public AnotherClass1() {}

    @Override
    public int getNumber() {
        return 1;
    }
}

class AnotherClass2 implements AnotherClass {

    public AnotherClass2() {}

    @Override
    public int getNumber() {
        return 2;
    }
}

class AnotherClass3 implements AnotherClass {

    public AnotherClass3() {}

    @Override
    public int getNumber() {
        return 3;
    }
}

@Singleton
class MyClass1 {
    private final MyClass2 class2;
    private int number = 0;

    @Inject
    public MyClass1(MyClass2 anotherClass) {
        class2 = anotherClass;
    }

//    @Inject
//    public MyClass1(int number1, int number2, MyClass2 anotherClass) {
//        number = number1 + number2;
//        class2 = anotherClass;
//    }

    public int getNumber() {
        return class2.getNumber();
    }
    public int getAnotherNumber() {
        number++;
        return number;
    }
}

class MyClass2 {
    private final MyClass3 class2;

    @Inject
    public MyClass2(MyClass3 anotherClass) {
        class2 = anotherClass;
    }

    public int getNumber() {
        return class2.getNumber();
    }
}

class MyClass3 {
    private final MyClass4 class2;

    @Inject
    public MyClass3(MyClass4 anotherClass) {
        class2 = anotherClass;
    }

    public int getNumber() {
        return class2.getNumber();
    }
}


class MyClass4 {

    private final AnotherClass class2;

    @Inject
    public MyClass4(AnotherClass anotherClass) {
        class2 = anotherClass;
    }

    public int getNumber() {
        return class2.getNumber();
    }
}

public class Main {
    public static void main(String[] args) throws NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException, DiFrameworkException {
//        Class<MyClass1> myClass = MyClass1.class;
//        Class[] params = {AnotherClass.class};
//        System.out.println(myClass.getDeclaredConstructor(params));
//        for (Method method : myClass.getDeclaredMethods()) {
//            if (method.isAnnotationPresent(Inject.class)) {
//                Inject annotation = method.getAnnotation(Inject.class);
//                System.out.println(
//                        String.format("found annotated method '%s', arg %s",
//                                method.getName(), annotation.toString()));
//            }
//        }
//        for (Constructor<?> constructor : myClass.getDeclaredConstructors()) {
//            if (constructor.isAnnotationPresent(Inject.class)) {
//                for (Class<?> parameter : constructor.getParameterTypes()) {
//                    System.out.println(parameter.getName());
//                }
//            }
//        }
//        Map<Class<?>, List<Class<?>>> map2 = new HashMap<>();
//        map2.put(MyClass1.class, new ArrayList<Class<?>>());
//        map2.get(MyClass1.class).add(AnotherClass.class);
//        map2.get(MyClass1.class).add(AnotherClass1.class);
//        map2.get(MyClass1.class).add(AnotherClass2.class);
//        System.out.println(map2.get(MyClass1.class));
//        Map<Class<?>, Class<?>> map = new HashMap<>();
//        map.put(AnotherClass.class, AnotherClass1.class);
//        map.put(MyClass1.class, null);
//        MyClass1 class1 = null;
//        for (Constructor<?> constructor : myClass.getDeclaredConstructors()) {
//            if (constructor.isAnnotationPresent(Inject.class)) {
//                Class<?>[] parameters = constructor.getParameterTypes();
//                Constructor<?> constr = map.get(parameters[0]).getConstructor();
//                class1 = (MyClass1) constructor.newInstance(constr.newInstance());
//            }
//        }
//        assert class1 != null;
//        System.out.println(class1.getNumber());

        try {
            DiFramework myDi = new DiFrameworkImpl();
            myDi.register(MyClass1.class);
            myDi.register(MyClass2.class);
            myDi.register(MyClass3.class);
            myDi.register(MyClass4.class);
            myDi.register(AnotherClass.class, AnotherClass2.class);
            myDi.completeRegistration();
            MyClass1 class1 = (MyClass1) myDi.resolve(MyClass1.class);
            MyClass1 class12 = (MyClass1) myDi.resolve(MyClass1.class);
            MyClass1 class123 = (MyClass1) myDi.resolve(MyClass1.class);
            System.out.println(class1.getAnotherNumber());
            System.out.println(class12.getAnotherNumber());
            System.out.println(class123.getAnotherNumber());
        } catch(DiFrameworkException e) {
            System.err.println(e.getMessage());
        }
    }
}
