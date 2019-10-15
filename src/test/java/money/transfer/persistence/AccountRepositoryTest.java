package money.transfer.persistence;

import com.google.inject.Inject;
import money.transfer.BaseInjector;
import money.transfer.entity.Account;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountRepositoryTest extends BaseInjector {

    private AccountRepository accountRepository = injector.getInstance(AccountRepository.class);;

    @Test
    public void testSaveAccount() {
        Account account = Account.builder()
                .id(UUID.randomUUID())
                .accountOwnerName("Vinothkumar")
                .accountBalance(new BigDecimal(0))
                .build();
        accountRepository.save(account);
        assertEquals("Vinothkumar", accountRepository.get(account.getId()).getAccountOwnerName());
    }

    @Test
    public void testUpdateAccountOwnerName() {
        Account account = Account.builder()
                .id(UUID.randomUUID())
                .accountOwnerName("Vinothkumar")
                .accountBalance(new BigDecimal(0))
                .build();
        accountRepository.save(account);
        account.setAccountOwnerName("new name");
        accountRepository.update(account);
        assertEquals("new name", accountRepository.get(account.getId()).getAccountOwnerName());
    }

    @Test
    public void testUpdateAccountBalance() {
        Account account = Account.builder()
                .id(UUID.randomUUID())
                .accountOwnerName("Vinothkumar")
                .accountBalance(new BigDecimal(0))
                .build();
        accountRepository.save(account);
        account.setAccountBalance(new BigDecimal(99));
        accountRepository.update(account);
        assertEquals(new BigDecimal(99), accountRepository.get(account.getId()).getAccountBalance());
    }
}
