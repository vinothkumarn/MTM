package money.transfer.service;

import com.google.inject.Inject;
import money.transfer.controller.dto.CreditDto;
import money.transfer.persistence.AccountRepository;

import java.math.BigDecimal;
import java.util.UUID;

public class TransferService {

  private final AccountRepository accountRepository;

  @Inject
  public TransferService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  // Synchronized keyword is used to avoid race conditions
  public synchronized void credit(CreditDto creditDto) {
          accountRepository.getAndUpdateAmount(creditDto.getId(), creditDto.getCreditAmount());
  }

  // Synchronized keyword is used to avoid race conditions
  public synchronized void moneyTransfer(UUID senderId, UUID receiverId, BigDecimal amount) {
      // Debit amount from sender
      accountRepository.getAndUpdateAmount(senderId, amount.negate());
      // Credit amount to receiver
      accountRepository.getAndUpdateAmount(receiverId, amount);
  }
}
