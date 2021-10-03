package SimpleTestClasses;

import javax.inject.Inject;

public class Engine {
    @Inject
    public Engine() {
        this.rpm = 0;
    }
    int rpm = 1000;
}
