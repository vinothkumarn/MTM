package money.transfer.controller;

import money.transfer.controller.dto.AccountDTO;
import money.transfer.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.IllegalFormatException;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountControllerTest {

  @InjectMocks
  private AccountService accountService;

  @Test
  void shouldThrowExceptionIfNameIsInvalid() {
    final AccountDTO accountDto = AccountDTO.builder()
            .accountOwnerName("123456")
            .build();

    assertThrows(IllegalFormatException.class, () -> accountService.createAccount(accountDto));
  }
}
