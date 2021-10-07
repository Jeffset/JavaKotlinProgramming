package com.lab2;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;

public class DiFrameworkImpl implements DiFramework {

    private Map<Class<?>, List<Class<?>>> graph;
    private Map<Class<?>, Class<?>> implementations;
    private Map<Class<?>, Object> instances;
    private Map<Class<?>, Constructor<?>> constructors;
    private Set<Class<?>> all_classes;
    private boolean registration_is_completed;

    public DiFrameworkImpl() {
        graph = new HashMap<>();
        implementations = new HashMap<>();
        instances = new HashMap<>();
        constructors = new HashMap<>();
        all_classes = new HashSet<>();
        registration_is_completed = false;
    }

    @Override
    public Object resolve(Class<?> some_class) throws NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> implementation = implementations.get(some_class);
        if (implementation.isAnnotationPresent(Singleton.class)) {
            if (instances.containsKey(implementation)) {
                return instances.get(implementation);
            }
        }

        List<Class<?>> services = graph.get(some_class);
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
        checkingExceptions(some_class);
        all_classes.add(some_class);
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
        checkingExceptions(some_interface, implementation);
        all_classes.add(some_interface);
        all_classes.add(implementation);
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
        Set<Class<?>> visited = new HashSet<>();

        for (Class<?> some_class : graph.keySet()) {
            if (visited.contains(some_class)) {
                continue;
            }
            checkingRegistration(some_class, visited);
        }
    }

    public void checkingRegistration(Class<?> some_class, Set<Class<?>> visited) {
        if (!implementations.containsKey(some_class)) {
            throw new DiFrameworkException("registration isn't completed");
        }
        visited.add(some_class);
        List<Class<?>> services = graph.get(some_class);
        for (var service : services) {
            if (visited.contains(service)) {
                continue;
            }
            checkingRegistration(service, visited);
        }
    }

    public void checkingExceptions(Class<?> some_interface, Class<?> implementation) {
        if (registration_is_completed) {
            throw new DiFrameworkException("registration after completed registration");
        }
        if (!some_interface.isInterface()) {
            throw new DiFrameworkException("expected class isn't interface");
        }
        if (implementation.isInterface()) {
            throw new DiFrameworkException("interface without implementation");
        }
        if (all_classes.contains(some_interface) || all_classes.contains(implementation)) {
            throw new DiFrameworkException("multiple class registration");
        }
    }

    public void checkingExceptions(Class<?> some_class) {
        if (registration_is_completed) {
            throw new DiFrameworkException("registration after completed registration");
        }
        if (some_class.isInterface()) {
            throw new DiFrameworkException("interface without implementation");
        }
        if (all_classes.contains(some_class)) {
            throw new DiFrameworkException("multiple class registration");
        }
    }
}
