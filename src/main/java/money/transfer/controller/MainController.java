package money.transfer.controller;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import money.transfer.config.MtmModule;
import money.transfer.controller.dto.AccountCreatedResponseDto;
import money.transfer.controller.dto.CreateAccountDto;
import money.transfer.controller.dto.CreditDto;
import money.transfer.controller.dto.TransferDto;
import money.transfer.entity.Account;
import money.transfer.service.AccountService;
import money.transfer.service.TransferService;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.List;
import java.util.UUID;

import static spark.Spark.*;

public class MainController {

    private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new MtmModule());
        AccountService accountService = injector.getInstance(AccountService.class);
        TransferService transferService = injector.getInstance(TransferService.class);

        path("/api", () -> {
            path("/accounts", () -> {

                get("/", (request, response) -> {
                    response.type("application/json");
                    List<Account> account = accountService.getAllAccount();
                    return new Gson().toJson(account);
                });

                post("/createAccount", (request, response) -> {
                    response.type("application/json");
                    CreateAccountDto createAccountDto = new Gson().fromJson(request.body(), CreateAccountDto.class);
                    String accountNumber = accountService.createAccount(createAccountDto).toString();
                    LOGGER.log(Level.INFO, "Account created");
                    response.status(201);
                    return new Gson()
                            .toJson(new AccountCreatedResponseDto(accountNumber));
                });

                get("/:id", (request, response) -> {
                    response.type("application/json");
                    try {
                        // UUID conversion will fail if invalid account number
                        Account account = accountService.getAccount(UUID.fromString(request.params(":id")));
                        return new Gson().toJson(account);
                    } catch (Exception e) {
                        LOGGER.log(Level.INFO, "Invalid account number");
                        return "Account doesn't exist. Please check the account number";
                    }
                });

                post("/credit", (request, response) -> {
                    response.type("application/json");
                    try {
                        CreditDto creditDto = new Gson().fromJson(request.body(), CreditDto.class);
                        transferService.credit(creditDto);
                        return "Account credited successfully";
                    } catch (Exception e) {
                        LOGGER.log(Level.INFO, "Crediting account failed");
                        return "Failed Operation.Invalid request data";
                    }
                });

                post("/transfer", (request, response) -> {
                    response.type("application/json");
                    try {
                        TransferDto transferDto = new Gson().fromJson(request.body(), TransferDto.class);
                        transferService.moneyTransfer(transferDto.getSenderId(),
                                transferDto.getReceiverId(),
                                transferDto.getAmount());
                        return "Amount transferred successfully";
                    } catch (Exception e) {
                        LOGGER.log(Level.INFO, "Money transfer account failed");
                        return "Failed Operation.Invalid request data";
                    }
                });

            });

        });
  }
}
