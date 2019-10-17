package money.transfer.service;

import com.google.inject.Inject;
import money.transfer.controller.dto.CreateAccountDto;
import money.transfer.controller.mapper.AccountMapper;
import money.transfer.entity.Account;
import money.transfer.persistence.AccountRepository;
import money.transfer.utils.ValidateName;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountService {

  private final AccountRepository accountRepository;
  private final static Logger LOGGER =
          Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  @Inject
  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public UUID createAccount(CreateAccountDto createAccountDtO) {
    if (createAccountDtO != null && ValidateName.validate(createAccountDtO.getAccountOwnerName())) {
      UUID createdAccountNumber =
          accountRepository.save(AccountMapper.createAccountDtoToAccount(createAccountDtO));
      return createdAccountNumber;
    } else {
      LOGGER.log(Level.INFO, "AccountService: account creation failed");
      throw new IllegalArgumentException("Invalid data");
    }
  }

  public Account getAccount(UUID id) {
    return accountRepository.get(id);
  }

  public List<Account> getAllAccount() { return accountRepository.getAll(); }
}
