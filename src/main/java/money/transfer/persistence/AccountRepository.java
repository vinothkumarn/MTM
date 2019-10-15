package money.transfer.persistence;

import money.transfer.entity.Account;

import java.util.UUID;

public interface AccountRepository {

    public Account save(Account account);
    public Account update(Account account);
    public Account get(UUID id);
}
