package money.transfer.persistence;

import com.google.inject.Inject;
import money.transfer.entity.Account;
import org.hibernate.SessionFactory;

import java.util.UUID;

public class AccountRepositoryImpl implements AccountRepository {

    private final SessionFactory sessionFactory;

    @Inject
    public AccountRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Account save(Account account) {
        return null;
    }

    @Override
    public Account update(Account account) {
        return null;
    }

    @Override
    public Account get(UUID id) {
        return null;
    }
}
