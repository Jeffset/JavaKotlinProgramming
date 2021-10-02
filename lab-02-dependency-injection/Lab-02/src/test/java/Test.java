import static org.junit.jupiter.api.Assertions.assertEquals;
import DependencyInjector.DependencyInjector;
import DependencyInjector.DependencyInjectorImpl;

public class Test {
    @org.junit.jupiter.api.Test
    public void TestDI() {
        DependencyInjector myDi = new DependencyInjectorImpl();
        myDi.register(Car.class);
        myDi.register(Engine.class);
        myDi.completeRegistration();
        Car new_car = (Car) myDi.resolve(Car.class);
        new_car.Beep();
    }
}
