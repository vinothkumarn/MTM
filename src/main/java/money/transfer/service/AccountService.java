package money.transfer.service;

import com.google.inject.Inject;
import money.transfer.controller.dto.CreateAccountDto;
import money.transfer.controller.mapper.AccountMapper;
import money.transfer.entity.Account;
import money.transfer.persistence.AccountRepository;
import money.transfer.utils.ValidateName;

import java.util.UUID;

public class AccountService {

  private final AccountRepository accountRepository;

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
      throw new IllegalArgumentException("Invalid data");
    }
  }

  public Account getAccount(UUID id) {
    return accountRepository.get(id);
  }
}
