package money.transfer.controller;

import money.transfer.entity.Account;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import money.transfer.controller.dto.AccountDTO;
import money.transfer.service.AccountService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountControllerTest {

  @InjectMocks
  private AccountService accountService;

  @Test
  void shouldCreateNewAccount() {
    final AccountDTO accountDto = AccountDTO.builder()
            .accountOwnerName("vinoth kumar")
            .build();
    Account account = accountService.createAccount(accountDto);
    UUID createdAccountId = account.getId();
    assertEquals("vinoth kumar", accountService.getAccount(createdAccountId).getAccountOwnerName());
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
