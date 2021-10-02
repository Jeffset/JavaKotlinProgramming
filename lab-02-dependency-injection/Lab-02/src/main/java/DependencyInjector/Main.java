package DependencyInjector;

import javax.inject.Inject;

class Slave {
    int a;
    @Inject
    Slave(int a) {
        this.a = a;
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("HelloWorld");
        Slave test = new Slave(2);
        String name = test.toString();

    }
}
