package money.transfer.controller.mapper;

import money.transfer.controller.dto.CreateAccountDto;
import money.transfer.entity.Account;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountMapper {

    public static CreateAccountDto accountToCreateAccountDto(Account account) {
        return CreateAccountDto.builder()
                .accountOwnerName(account.getAccountOwnerName())
                .build();
    }

    public static Account createAccountDtoToAccount(CreateAccountDto createAccountDto) {
        return Account.builder()
                .id(UUID.randomUUID())
                .accountOwnerName(createAccountDto.getAccountOwnerName())
                .accountBalance(new BigDecimal(0))
                .build();
    }
}
