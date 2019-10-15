package money.transfer.service;

import money.transfer.entity.Account;
import money.transfer.persistence.AccountRepository;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import money.transfer.controller.dto.AccountDTO;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldCreateNewAccount() {

        when(accountRepository.save(any(Account.class))).thenReturn(Account.builder()
                .accountOwnerName("vinoth kumar")
                .build());

        final AccountDTO accountDto = AccountDTO.builder()
                .accountOwnerName("vinoth kumar")
                .build();
        Account account = accountService.createAccount(accountDto);
        assertEquals("vinoth kumar", account.getAccountOwnerName());
    }

    @Test
    void shouldThrowExceptionIfNameIsInvalid() {
        final AccountDTO accountDto = AccountDTO.builder()
                .accountOwnerName("123456")
                .build();

        assertThrows(IllegalStateException.class, () -> accountService.createAccount(accountDto));
    }

    @Test
    void shouldThrowExceptionIfNameStartsWithSpace() {
        final AccountDTO accountDto = AccountDTO.builder()
                .accountOwnerName(" vinoth kumar")
                .build();

        assertThrows(IllegalStateException.class, () -> accountService.createAccount(accountDto));
    }

    @Test
    void shouldThrowExceptionIfDtoIsNull() {
        final AccountDTO accountDto = null;
        assertThrows(IllegalStateException.class, () -> accountService.createAccount(accountDto));
    }

    @Test
    void shouldReturnAccountNumberInUuidFormat() {
        final AccountDTO accountDto = AccountDTO.builder()
                .accountOwnerName("vinoth kumar")
                .build();
        Account account = accountService.createAccount(accountDto);
        assertTrue(account.getId() instanceof UUID);
    }

    @Test
    void shouldReturnAccountBalanceAsZeroForNewAccount() {
        final AccountDTO accountDto = AccountDTO.builder()
                .accountOwnerName("vinoth kumar")
                .build();
        Account account = accountService.createAccount(accountDto);
        assertEquals(0, account.getAccountBalance());
    }

}
