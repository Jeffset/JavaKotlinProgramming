package SingletonTestClasses;

import javax.inject.Inject;

public class SingletonDependency {

    @Inject
    public SingletonDependency() {

    }
    public int getValue() {
        return 42;
    }
}
