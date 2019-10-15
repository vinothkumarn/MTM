package money.transfer.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import money.transfer.persistence.AccountRepository;
import money.transfer.persistence.AccountRepositoryImpl;
import money.transfer.service.AccountService;
import money.transfer.service.TransferService;
import org.hibernate.SessionFactory;

public class MtmModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AccountRepository.class).to(AccountRepositoryImpl.class);
        bind(AccountService.class);
        bind(TransferService.class);
    }

    @Provides
    @Singleton
    public SessionFactory provideSessionFactory() {
        return HibernateConfig.getSessionFactory();
    }
}
