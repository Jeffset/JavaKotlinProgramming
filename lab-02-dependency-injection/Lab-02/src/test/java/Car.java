import javax.inject.Inject;

public class Car {
    private final Engine engine;
    @Inject
    public Car(Engine eng) {
        this.engine = eng;
    }
    public void Beep() {
        System.out.println("beep");
    }
}