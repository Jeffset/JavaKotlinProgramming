package ComplexTestClasses;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SingletonComplexDep {
    @Inject
    public SingletonComplexDep() {
    }
    int i = 10;

    public int getInt() {
        return i;
    }
}
