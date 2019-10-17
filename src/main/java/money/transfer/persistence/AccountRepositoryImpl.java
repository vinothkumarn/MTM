package money.transfer.persistence;

import com.google.inject.Inject;
import money.transfer.entity.Account;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountRepositoryImpl implements AccountRepository {

    private final SessionFactory sessionFactory;
    private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Inject
    public AccountRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public UUID save(Account account) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UUID savedAccountNumber = (UUID) session.save(account);
        transaction.commit();
        return savedAccountNumber;
    }

    @Override
    @Transactional
    public void update(Account account) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(account);
        transaction.commit();
    }

    @Override
    @Transactional
    public Account get(UUID id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Account account = session.get(Account.class, id);
        transaction.commit();
        return account;
    }

    @Override
    @Transactional
    public List<Account> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Account");
        List<Account> results = query.list();
        transaction.commit();
        return results;
    }

    @Override
    @Transactional
    public void getAndUpdateAmount(UUID id, BigDecimal amount) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        // Synchronized keyword is not enough because in multi cluster environment,
        // different instances of this microservice may be trying to read and update
        // the same row concurrently. Hence a LOCK on the row in DB level is required.
        // Hence PESSIMISTIC_WRITE is used.
        Account account = session.get(Account.class, id, LockMode.PESSIMISTIC_WRITE);

        if (account == null) {
            transaction.rollback();
            LOGGER.log(Level.INFO, "AccountRepo: Invalid account number");
            throw new IllegalArgumentException("Account doesn't exist");
        }

        BigDecimal newBalance = account.getAccountBalance().add(amount);

        account.setAccountBalance(newBalance);
        session.saveOrUpdate(account);
        transaction.commit();
    }

    @Override
    @Transactional
    public void transferAmountAtomicOperation(UUID senderId, UUID receiverId, BigDecimal amount) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        // Synchronized keyword is not enough because in multi cluster environment,
        // different instances of this microservice may be trying to read and update
        // the same row concurrently. Hence a LOCK on the row in DB level is required.
        // Hence PESSIMISTIC_WRITE is used.
        Account senderAccount = session.get(Account.class, senderId, LockMode.PESSIMISTIC_WRITE);

        if (senderAccount == null) {
            transaction.rollback();
            LOGGER.log(Level.INFO, "AccountRepo: Invalid sender account number");
            throw new IllegalArgumentException("Sender account doesn't exist");
        }

        BigDecimal newSenderBalance = senderAccount.getAccountBalance().subtract(amount);

        if (newSenderBalance.signum() < 0) {
            transaction.rollback();
            LOGGER.log(Level.INFO, "AccountRepo: Account doesn't have money for this transfer");
            throw new IllegalStateException("Not enough money to make this transfer");
        }

        Account receiverAccount = session.get(Account.class, receiverId, LockMode.PESSIMISTIC_WRITE);

        if (receiverAccount == null) {
            transaction.rollback();
            LOGGER.log(Level.INFO, "AccountRepo: Invalid receiver account number");
            throw new IllegalArgumentException("Receiver account doesn't exist");
        }

        BigDecimal newReceiverBalance = receiverAccount.getAccountBalance().add(amount);

        senderAccount.setAccountBalance(newSenderBalance);
        receiverAccount.setAccountBalance(newReceiverBalance);
        session.saveOrUpdate(senderAccount);
        session.saveOrUpdate(receiverAccount);
        transaction.commit();
    }
}
