package com.lab2;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiFrameworkImpl implements DiFramework {

    private Map<Class<?>, List<Class<?>>> graph;
    private Map<Class<?>, Class<?>> implementations;
    private Map<Class<?>, Object> instances;
    private Map<Class<?>, Constructor<?>> constructors;
    private boolean registration_is_completed;

    public DiFrameworkImpl() {
        graph = new HashMap<>();
        implementations = new HashMap<>();
        instances = new HashMap<>();
        constructors = new HashMap<>();
        registration_is_completed = false;
    }

    @Override
    public Object resolve(Class<?> some_class) throws NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> implementation = implementations.get(some_class);
        if (some_class.isAnnotationPresent(Singleton.class)) {
            if (instances.containsKey(implementation)) {
                return instances.get(implementation);
            }
        }

        List<Class<?>> services = graph.get(some_class);
        if (services == null) {
            return implementation.getConstructor().newInstance();
        }
        List<Object> implementations_of_services = new ArrayList<>();
        for (var service : services) {
            implementations_of_services.add(resolve(service));
        }

        Object result_class = constructors.get(implementation).
                newInstance(implementations_of_services.toArray());
        if (some_class.isAnnotationPresent(Singleton.class)) {
            instances.put(implementation, result_class);
        }
        return result_class;
    }

    @Override
    public void register(Class<?> some_class) throws DiFrameworkException {
        if (registration_is_completed) {
            throw new DiFrameworkException("registration after completed registration");
        }
        if (some_class.isInterface()) {
            throw new DiFrameworkException("interface without implementation");
        }
        if (implementations.containsKey(some_class)) {
            throw new DiFrameworkException("multiple class registration");
        }

        implementations.put(some_class, some_class);

        int inject_constructor_counter = 0;
        for (Constructor<?> constructor : some_class.getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                inject_constructor_counter++;
                if (inject_constructor_counter > 1) {
                    throw new DiFrameworkException("found multiple @Inject annotated constructors");
                }
                if (Modifier.isPrivate(constructor.getModifiers())) {
                    throw new DiFrameworkException("found private @Inject annotated constructor");
                }
                List<Class<?>> parameters = List.of(constructor.getParameterTypes());
                graph.put(some_class, new ArrayList<>(parameters));
                constructors.put(some_class, constructor);
            }
        }
        if (inject_constructor_counter == 0) {
            throw new DiFrameworkException("@Inject constructor not found");
        }
    }

    @Override
    public void register(Class<?> some_interface, Class<?> implementation)
            throws DiFrameworkException {
        if (registration_is_completed) {
            throw new DiFrameworkException("registration after completed registration");
        }
        if (!some_interface.isInterface()) {
            throw new DiFrameworkException("expected class isn't interface");
        }
        if (implementation.isInterface()) {
            throw new DiFrameworkException("interface without implementation");
        }
        if (implementations.containsKey(some_interface)) {
            throw new DiFrameworkException("multiple class registration");
        }

        implementations.put(some_interface, implementation);

        int inject_constructor_counter = 0;
        for (Constructor<?> constructor : implementation.getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                inject_constructor_counter++;
                if (inject_constructor_counter > 1) {
                    throw new DiFrameworkException("found multiple @Inject annotated constructors");
                }
                List<Class<?>> parameters = List.of(constructor.getParameterTypes());
                graph.put(some_interface, new ArrayList<>(parameters));
                constructors.put(implementation, constructor);
            }
        }
        if (inject_constructor_counter == 0) {
            throw new DiFrameworkException("@Inject constructor not found");
        }
    }

    @Override
    public void completeRegistration() {
        registration_is_completed = true;
    }
}
