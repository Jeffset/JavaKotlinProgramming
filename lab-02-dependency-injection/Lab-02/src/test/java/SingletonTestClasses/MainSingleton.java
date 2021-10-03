package SingletonTestClasses;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainSingleton {
    SlaveSingleton ss = null;

    @Inject
    public MainSingleton(SlaveSingleton ss) {
        this.ss = ss;
    }

    public SlaveSingleton getSlaveSingleton() {
        return ss;
    }
}
