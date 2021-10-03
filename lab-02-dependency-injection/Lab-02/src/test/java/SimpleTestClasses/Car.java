package SimpleTestClasses;

import javax.inject.Inject;

public class Car {
    private final Engine engine;
    @Inject
    public Car(Engine eng) {
        this.engine = eng;
    }
    public String Beep() {
        return "beep";
    }
    public Engine getEngine() {
        return engine;
    }
}
