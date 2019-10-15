package money.transfer.persistence;

import money.transfer.entity.Account;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountRepository {

    public UUID save(Account account);
    public void update(Account account);
    public Account get(UUID id);
    public Account getAndUpdateAmount(UUID id, BigDecimal amount);
}
