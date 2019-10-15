package money.transfer.service;

import com.google.inject.Inject;
import money.transfer.controller.dto.AccountDTO;
import money.transfer.entity.Account;
import money.transfer.persistence.AccountRepository;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountService {

  private final AccountRepository accountRepository;

  @Inject
  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Account createAccount(AccountDTO accountDTO) {
    return accountRepository.save(null);
  }

  public Account getAccount(UUID id) {
    return null;
  }
}
