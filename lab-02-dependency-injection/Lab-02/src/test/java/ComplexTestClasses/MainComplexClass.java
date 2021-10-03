package ComplexTestClasses;

import javax.inject.Inject;

public class MainComplexClass {
    FirstDependency fd;
    SecondDependency sd;
    @Inject
    public MainComplexClass(FirstDependency fd, SecondDependency sd) {
        this.fd = fd;
        this.sd = sd;
    }

    public SecondDependency getSecond() {
        return sd;
    }
}
