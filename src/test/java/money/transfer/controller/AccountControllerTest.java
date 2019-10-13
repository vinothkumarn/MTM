package money.transfer.controller;

import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import money.transfer.controller.dto.AccountDTO;
import money.transfer.service.AccountService;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountControllerTest {

  @InjectMocks
  private AccountService accountService;

  @Test
  void shouldThrowExceptionIfNameIsInvalid() {
    final AccountDTO accountDto = AccountDTO.builder()
            .accountOwnerName("123456")
            .build();

    assertThrows(IllegalStateException.class, () -> accountService.createAccount(accountDto));
  }
}
