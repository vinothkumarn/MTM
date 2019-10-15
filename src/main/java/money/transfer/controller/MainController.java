package money.transfer.controller;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import money.transfer.config.MtmModule;
import money.transfer.controller.dto.AccountCreatedResponseDto;
import money.transfer.controller.dto.CreateAccountDto;
import money.transfer.controller.dto.CreditDto;
import money.transfer.controller.dto.TransferDto;
import money.transfer.entity.Account;
import money.transfer.service.AccountService;
import money.transfer.service.TransferService;

import java.util.UUID;

import static spark.Spark.*;

public class MainController {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new MtmModule());
        AccountService accountService = injector.getInstance(AccountService.class);
        TransferService transferService = injector.getInstance(TransferService.class);

        path("/api", () -> {
            path("/accounts", () -> {

                post("/createAccount", (request, response) -> {
                    response.type("application/json");
                    CreateAccountDto createAccountDto = new Gson().fromJson(request.body(), CreateAccountDto.class);
                    String accountNumber = accountService.createAccount(createAccountDto).toString();
                    return new Gson()
                            .toJson(new AccountCreatedResponseDto(accountNumber));
                });

                get("/:id", (request, response) -> {
                    response.type("application/json");
                    Account account = accountService.getAccount(UUID.fromString(request.params(":id")));
                    return new Gson().toJson(account);
                });

                post("/credit", (request, response) -> {
                    response.type("application/json");
                    CreditDto creditDto = new Gson().fromJson(request.body(), CreditDto.class);
                    transferService.credit(creditDto);
                    return "Account credited successfully";
                });

                post("/transfer", (request, response) -> {
                    response.type("application/json");
                    TransferDto transferDto = new Gson().fromJson(request.body(), TransferDto.class);
                    transferService.moneyTransfer(transferDto.getSenderId(),
                            transferDto.getReceiverId(),
                            transferDto.getAmount());
                    return "Account transferred successfully";
                });

            });

        });
  }
}
