package SingletonTestClasses;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SlaveSingleton {
    SingletonDependency dep = null;

    @Inject
    public SlaveSingleton(SingletonDependency d) {
        dep = d;
    }

    public SingletonDependency getDependency() {
        return dep;
    }
}
