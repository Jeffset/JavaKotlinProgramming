package BrokenClasses;

import javax.inject.Inject;

public class TwoInject {
    @Inject
    public TwoInject() {
        System.out.println("helo");
    }
    @Inject
    public TwoInject(int a) {
        System.out.println("helo" + a);
    }
}
