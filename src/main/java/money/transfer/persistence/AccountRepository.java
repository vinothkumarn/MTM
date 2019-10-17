package money.transfer.persistence;

import money.transfer.entity.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountRepository {

    public UUID save(Account account);
    public void update(Account account);
    public Account get(UUID id);
    public List<Account> getAll();
    public void getAndUpdateAmount(UUID id, BigDecimal amount);
    public void transferAmountAtomicOperation(UUID senderId, UUID receiverId, BigDecimal amount);
}
