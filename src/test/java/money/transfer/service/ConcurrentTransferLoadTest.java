package money.transfer.service;

import money.transfer.BaseInjector;
import money.transfer.controller.dto.CreateAccountDto;
import money.transfer.controller.dto.CreditDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConcurrentTransferLoadTest extends BaseInjector {

  private int numberOfThreads = 1000;
  private AccountService accountService = injector.getInstance(AccountService.class);
  private TransferService transferService = injector.getInstance(TransferService.class);
  private ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
  private Collection<Future> futures = new LinkedList<Future>();

  @Test
  public void testConcurrentCredits() throws ExecutionException, InterruptedException {

    List<UUID> accounts = new ArrayList<UUID>();
    CreateAccountDto user1Dto =
            CreateAccountDto.builder().accountOwnerName("User one").build();
    CreateAccountDto user2Dto =
            CreateAccountDto.builder().accountOwnerName("User two").build();
    accounts.add(accountService.createAccount(user1Dto));
    accounts.add(accountService.createAccount(user2Dto));

    Runnable runnable =
        () -> {
          for (UUID accountId : accounts) {
            CreditDto creditDto =
                CreditDto.builder().id(accountId).creditAmount(new BigDecimal(50)).build();
            transferService.credit(creditDto);
          }
        };

    for (int i = 0; i < numberOfThreads; i++) {
      futures.add(executor.submit(runnable));
    }

    // Wait for all threads to complete
    for (Future future : futures) {
      future.get();
    }

    for (UUID accountId : accounts) {

      // After crediting 1000 times, balance should be 50000 in both accounts
      assertEquals(
          new BigDecimal(50000).setScale(2, RoundingMode.HALF_EVEN),
          accountService
              .getAccount(accountId)
              .getAccountBalance()
              .setScale(2, RoundingMode.HALF_EVEN));
    }
  }

  @Test
  public void testConcurrentTransfers() throws ExecutionException, InterruptedException {

    List<UUID> accounts = new ArrayList<UUID>();
    CreateAccountDto user1Dto =
            CreateAccountDto.builder().accountOwnerName("User one").build();
    CreateAccountDto user2Dto =
            CreateAccountDto.builder().accountOwnerName("User two").build();
    accounts.add(accountService.createAccount(user1Dto));
    accounts.add(accountService.createAccount(user2Dto));

    // Credit accounts before making transfer
    for (UUID accountId : accounts) {
      CreditDto creditDto =
          CreditDto.builder().id(accountId).creditAmount(new BigDecimal(1000)).build();
      transferService.credit(creditDto);
    }
    Runnable runnable =
        () -> {
          transferService.moneyTransfer(accounts.get(0), accounts.get(1), new BigDecimal(1));
        };

    for (int i = 0; i < numberOfThreads; i++) {
      futures.add(executor.submit(runnable));
    }

    // Wait for all threads to complete
    for (Future future : futures) {
      future.get();
    }

    // After transferring 1 EUR 1000 times, balance should be 0 and 2000 in both accounts
    // respectively
    assertEquals(
        new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN),
        accountService
            .getAccount(accounts.get(0))
            .getAccountBalance()
            .setScale(2, RoundingMode.HALF_EVEN));
    assertEquals(
        new BigDecimal(2000).setScale(2, RoundingMode.HALF_EVEN),
        accountService
            .getAccount(accounts.get(1))
            .getAccountBalance()
            .setScale(2, RoundingMode.HALF_EVEN));
  }
}
