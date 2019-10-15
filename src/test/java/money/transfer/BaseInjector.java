package money.transfer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import money.transfer.config.MtmModule;
import money.transfer.persistence.AccountRepository;

public abstract class BaseInjector {
    protected static Injector injector;

    static {
        injector = Guice.createInjector(new MtmModule());
    }

}
