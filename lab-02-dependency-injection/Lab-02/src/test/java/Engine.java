import javax.inject.Inject;

public class Engine {
    Starter starter;
    @Inject
    public Engine() {
        this.rpm = 0;
        this.starter = null;
    }
    public Engine(Starter starter, int rpm) {
        this.starter = starter;
        this.rpm = rpm;
    }

    int rpm = 1000;
}
