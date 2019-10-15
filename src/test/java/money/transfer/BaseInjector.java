package money.transfer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import money.transfer.config.MtmModule;

public abstract class BaseInjector {
    protected static Injector injector;

    static {
        injector = Guice.createInjector(new MtmModule());
    }

}
