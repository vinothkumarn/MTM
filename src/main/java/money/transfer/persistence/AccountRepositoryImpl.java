package money.transfer.persistence;

import com.google.inject.Inject;
import money.transfer.entity.Account;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;
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

    @Override
    public Account getAndUpdateAmount(UUID id, BigDecimal amount) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        // Synchronized keyword is not enough because in multi cluster environment,
        // different instances of this microservice may be trying to read and update
        // the same row concurrently. Hence a LOCK on the row in DB level is required.
        // Hence PESSIMISTIC_WRITE is used.
        Account account = session.get(Account.class, id, LockMode.PESSIMISTIC_WRITE);
        BigDecimal newBalance = account.getAccountBalance().add(amount);

        if (newBalance.signum() < 0) {
            throw new IllegalStateException("Not enough money to make this transfer");
        }
        account.setAccountBalance(newBalance);
        session.saveOrUpdate(account);
        transaction.commit();
        return account;
    }
}
