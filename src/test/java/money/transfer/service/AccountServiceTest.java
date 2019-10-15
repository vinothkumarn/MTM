package money.transfer.service;

import money.transfer.controller.dto.CreateAccountDto;
import money.transfer.controller.mapper.AccountMapper;
import money.transfer.entity.Account;
import money.transfer.persistence.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldCreateNewAccount() {

        final CreateAccountDto createAccountDto = CreateAccountDto.builder()
                .accountOwnerName("vinoth kumar")
                .build();

        Account account = AccountMapper.createAccountDtoToAccount(createAccountDto);

        when(accountRepository.save(any(Account.class))).thenReturn(account.getId());
        when(accountRepository.get(any(UUID.class))).thenReturn(account);

        UUID createdAccountNumber = accountService.createAccount(createAccountDto);

        assertEquals("vinoth kumar", accountService.getAccount(createdAccountNumber).getAccountOwnerName());
    }

    @Test
    void shouldThrowExceptionIfNameIsInvalid() {
        final CreateAccountDto createAccountDto = CreateAccountDto.builder()
                .accountOwnerName("123456")
                .build();

        assertThrows(IllegalArgumentException.class, () -> accountService.createAccount(createAccountDto));
    }

    @Test
    void shouldThrowExceptionIfNameStartsWithSpace() {
        final CreateAccountDto createAccountDto = CreateAccountDto.builder()
                .accountOwnerName(" vinoth kumar")
                .build();

        assertThrows(IllegalArgumentException.class, () -> accountService.createAccount(createAccountDto));
    }

    @Test
    void shouldThrowExceptionIfDtoIsNull() {
        final CreateAccountDto createAccountDto = null;
        assertThrows(IllegalArgumentException.class, () -> accountService.createAccount(createAccountDto));
    }

    @Test
    void shouldReturnAccountNumberInUuidFormat() {
        when(accountRepository.save(any(Account.class))).thenReturn(UUID.randomUUID());

        final CreateAccountDto createAccountDto = CreateAccountDto.builder()
                .accountOwnerName("vinoth kumar")
                .build();
        UUID createdAccountNumber = accountService.createAccount(createAccountDto);

        assertTrue(createdAccountNumber instanceof UUID);
    }

    @Test
    void shouldReturnAccountBalanceAsZeroForNewAccount() {

        final CreateAccountDto createAccountDto = CreateAccountDto.builder()
                .accountOwnerName("vinoth kumar")
                .build();
        Account account = AccountMapper.createAccountDtoToAccount(createAccountDto);

        assertEquals(new BigDecimal(0), account.getAccountBalance());
    }

}
