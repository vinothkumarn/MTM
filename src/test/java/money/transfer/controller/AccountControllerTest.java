package money.transfer.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.IllegalFormatException;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountControllerTest {

  @InjectMocks
  final AccountService accountService;

  @Test
  void shouldThrowExceptionIfNameIsInvalid() {
    final AccountDto accountDto = AccountDto.builder()
            .setAccountOwnerName("123456")
            .build();

    assertThrows(IllegalFormatException.class, accountService.createAccount(accountDto));
  }
}
