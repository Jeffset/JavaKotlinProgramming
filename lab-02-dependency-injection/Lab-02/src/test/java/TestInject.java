import BrokenClasses.NoInject;
import BrokenClasses.PrivateInject;
import BrokenClasses.TwoInject;
import ComplexTestClasses.FirstDependency;
import ComplexTestClasses.MainComplexClass;
import ComplexTestClasses.SecondDependency;
import ComplexTestClasses.SingletonComplexDep;
import DependencyInjector.DependencyInjector;
import DependencyInjector.DependencyInjectorImpl;
import SimpleTestClasses.Car;
import SimpleTestClasses.Engine;
import SingletonTestClasses.MainSingleton;
import SingletonTestClasses.SingletonDependency;
import SingletonTestClasses.SlaveSingleton;
import InterfaceClasses.TestInterface;
import InterfaceClasses.TestInterfaceImplementation;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestInject {
    @Test
    public void TestSimple() {
        DependencyInjector myDi = new DependencyInjectorImpl();
        myDi.register(Car.class);
        myDi.register(Engine.class);
        myDi.completeRegistration();
        Car car = (Car) myDi.resolve(Car.class);
        assertEquals(car.Beep(), "beep");
        Car another_car = (Car) myDi.resolve(Car.class);
        assertEquals(another_car.Beep(), "beep");
        assertNotEquals(another_car, car);
        assertNotEquals(another_car.getEngine(), car.getEngine());
    }

    @Test
    public void TestSingleton() {
        DependencyInjector myDi = new DependencyInjectorImpl();
        myDi.register(MainSingleton.class);
        myDi.register(SlaveSingleton.class);
        myDi.register(SingletonDependency.class);
        myDi.completeRegistration();
        MainSingleton sing_1 = (MainSingleton) myDi.resolve(MainSingleton.class);
        MainSingleton sing_2 = (MainSingleton) myDi.resolve(MainSingleton.class);
        assertEquals(sing_1, sing_2);
        assertEquals(sing_1.getSlaveSingleton(), sing_2.getSlaveSingleton());
        SlaveSingleton ssing_1 = (SlaveSingleton) myDi.resolve(SlaveSingleton.class);
        SlaveSingleton ssing_2 = (SlaveSingleton) myDi.resolve(SlaveSingleton.class);
        assertEquals(ssing_1, ssing_2);
        SingletonDependency sd_1 = (SingletonDependency) myDi.resolve(SingletonDependency.class);
        SingletonDependency sd_2 = (SingletonDependency) myDi.resolve(SingletonDependency.class);
        assertNotEquals(sd_1, sd_2);
    }
    @Test
    public void TestComplex() {
        DependencyInjector myDi = new DependencyInjectorImpl();
        myDi.register(MainComplexClass.class);
        myDi.register(FirstDependency.class);
        myDi.register(SecondDependency.class);
        myDi.register(SingletonComplexDep.class);
        myDi.completeRegistration();
        MainComplexClass mc1 = (MainComplexClass) myDi.resolve(MainComplexClass.class);
        MainComplexClass mc2 = (MainComplexClass) myDi.resolve(MainComplexClass.class);
        assertNotEquals(mc1, mc2);
        assertNotEquals(mc1.getSecond(), mc2.getSecond());
        assertEquals(mc1.getSecond().getSingle(), mc2.getSecond().getSingle());
        SingletonComplexDep scd1 = (SingletonComplexDep) myDi.resolve(SingletonComplexDep.class);
        SingletonComplexDep scd2 = (SingletonComplexDep) myDi.resolve(SingletonComplexDep.class);
        assertEquals(scd1, scd2);
    }

    @Test
    public void TestThrow() {
        DependencyInjector myDi = new DependencyInjectorImpl();
        assertThrows(RuntimeException.class, () -> myDi.register(NoInject.class));
        assertThrows(RuntimeException.class, () -> myDi.register(PrivateInject.class));
        assertThrows(UnsupportedOperationException.class, () -> myDi.register(TwoInject.class));
        assertThrows(UnsupportedOperationException.class, () -> myDi.resolve(myDi.getClass()));
        myDi.completeRegistration();
        assertThrows(UnsupportedOperationException.class, () -> myDi.resolve(myDi.getClass()));
    }
    @Test
    public void TestInterfaces() {
        DependencyInjector myDi = new DependencyInjectorImpl();
        myDi.register(TestInterface.class, TestInterfaceImplementation.class);
        myDi.completeRegistration();
        TestInterfaceImplementation impl = (TestInterfaceImplementation) myDi.resolve(TestInterface.class);
        assertEquals(impl.get(), "helo");
    }
}
