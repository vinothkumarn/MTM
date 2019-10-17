package money.transfer.service;

import com.google.inject.Inject;
import money.transfer.controller.dto.CreditDto;
import money.transfer.persistence.AccountRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransferService {

  private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
  private final AccountRepository accountRepository;

  @Inject
  public TransferService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  // Synchronized keyword is used to avoid race conditions
  @Transactional
  public synchronized void credit(CreditDto creditDto) {
      if(creditDto.getCreditAmount().signum() < 0) {
          LOGGER.log(Level.INFO, "Credit amount shouldn't be negative");
          throw new IllegalArgumentException("Amount shouldn't be negative");
      }
      accountRepository.getAndUpdateAmount(creditDto.getId(), creditDto.getCreditAmount());
  }

  // Synchronized keyword is used to avoid race conditions
  @Transactional
  public synchronized void moneyTransfer(UUID senderId, UUID receiverId, BigDecimal amount) {
      if(amount.signum() < 0) {
          LOGGER.log(Level.INFO, "Transfer amount shouldn't be negative");
          throw new IllegalArgumentException("Transfer amount shouldn't be negative");
      }
      accountRepository.transferAmountAtomicOperation(senderId, receiverId, amount);
  }
}
