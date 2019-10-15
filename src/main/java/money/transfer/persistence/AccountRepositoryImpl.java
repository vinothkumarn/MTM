package money.transfer.persistence;

import com.google.inject.Inject;
import money.transfer.entity.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.UUID;

public class AccountRepositoryImpl implements AccountRepository {

    private final SessionFactory sessionFactory;

    @Inject
    public AccountRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public UUID save(Account account) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UUID savedAccountNumber = (UUID) session.save(account);
        transaction.commit();
        return savedAccountNumber;
    }

    @Override
    public void update(Account account) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(account);
        transaction.commit();
    }

    @Override
    public Account get(UUID id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Account account = session.get(Account.class, id);
        transaction.commit();
        return account;
    }
}
