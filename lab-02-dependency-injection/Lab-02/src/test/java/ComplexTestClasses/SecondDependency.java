package ComplexTestClasses;

import javax.inject.Inject;

public class SecondDependency {
    SingletonComplexDep sd;

    public SingletonComplexDep getSingle() {
        return sd;
    }

    @Inject
    public SecondDependency(SingletonComplexDep sd) {
        this.sd = sd;
    }
}
