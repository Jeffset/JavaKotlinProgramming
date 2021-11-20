package DependencyInjector;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class DependencyInjectorImpl implements DependencyInjector {

    boolean isCompleteRegistration = false;

    List<Class<?>> classes = new ArrayList<>();

    Map<Class<?>, Object> singletons = new HashMap<>();
    Map<Class<?>, Class<?>> interfaces = new HashMap<>();
    Vector<Vector<Boolean>> dependencies = new Vector<>();

    @Override
    public void register(Class<?> cl) {
        if (isCompleteRegistration) {
            throw new RuntimeException("Already ended class registration");
        }
        if (!classes.contains(cl)) {
            Constructor<?> notUsed = getAnnotatedConstructor(cl);
            classes.add(cl);
        }
    }

    @Override
    public void register(Class<?> interf, Class<?> cl) {
        if(!interf.isInterface()) {
            throw new RuntimeException("First argument is not an interface");
        }
        if (isCompleteRegistration) {
            throw new RuntimeException("Already ended class registration");
        }
        if (!classes.contains(cl)) {
            Constructor<?> notUsed = getAnnotatedConstructor(cl);
            classes.add(cl);
        }
        interfaces.put(interf, cl);
    }

    @Override
    public void completeRegistration() {
        isCompleteRegistration = true;
        // create graph
        dependencies.setSize(classes.size());
        for (int i = 0; i < dependencies.size(); i++) {
            dependencies.set(i, new Vector<>());
            dependencies.get(i).setSize(classes.size());
            for (int j = 0; j < dependencies.get(i).size(); j++) {
                dependencies.get(i).set(j, false);
            }
        }
        for (var cl : classes) {
            EnhanceGraph(cl);
        }
        InstantiateSingletons();
    }

    private void InstantiateSingletons() {
        for (var cl : classes) {
            if (cl.isAnnotationPresent(Singleton.class)) {
                singletons.put(cl, GetInstance(cl));
            }
        }
    }

    private void EnhanceGraph(Class<?> cl) {
        Constructor<?> inject_constructor = getAnnotatedConstructor(cl);
        Class<?>[] parameterTypes = inject_constructor.getParameterTypes();
        int current_index = classes.indexOf(cl);
        for (var dependency_class : parameterTypes) {
            int dependency_index = classes.indexOf(dependency_class);
            dependencies.get(current_index).set(dependency_index, true);
            EnhanceGraph(dependency_class);
        }
    }

    private Object GetInstance(Class<?> cl) {
        if (cl.isAnnotationPresent(Singleton.class)) {
            if (singletons.containsKey(cl)) {
                return singletons.get(cl);
            }
        }

        return ConstructClass(cl);
    }

    private Object ConstructClass(Class<?> cl) {
        Object result = null;
        Constructor<?> constructor = getAnnotatedConstructor(cl);
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        List<Object> instantiated_objects = new ArrayList<>();
        for (var type : parameterTypes) {
            instantiated_objects.add(GetInstance(type));
        }
        Object[] array = instantiated_objects.toArray();
        try {
            result = constructor.newInstance(array);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Object resolve(Class<?> cl) {
        if (!isCompleteRegistration) {
            throw new UnsupportedOperationException("Registration is not complete");
        }
        if(cl.isInterface()) {
            if(interfaces.containsKey(cl)) {
                return GetInstance(interfaces.get(cl));
            } else {
                throw new UnsupportedOperationException("Interface " + cl.getName() + " wasn't registered");
            }
        }
        if (!classes.contains(cl)) {
            throw new UnsupportedOperationException("Class " + cl.getName() + " wasn't registered");
        }
        return GetInstance(cl);
    }

    private Constructor<?> getAnnotatedConstructor(Class<?> cl) {
        var constructors = cl.getConstructors();

        boolean gotInjectAnnotation = false;
        Constructor<?> inject_constructor = null;
        for (var constructor : constructors) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                if (gotInjectAnnotation) {
                    throw new UnsupportedOperationException("Found class with two or more constructors with @Inject");
                } else {
                    inject_constructor = constructor;
                    gotInjectAnnotation = true;
                }
            }
        }
        if (!gotInjectAnnotation) {
            throw new RuntimeException("Public constructors with @Inject not found");
        }
        return inject_constructor;
    }
}
