package BrokenClasses;

import javax.inject.Inject;

public class PrivateInject {
    @Inject
    PrivateInject() {
        System.out.println("helo");
    }
}
