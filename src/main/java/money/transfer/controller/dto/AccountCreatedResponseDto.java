package money.transfer.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountCreatedResponseDto {
    private String accountNumber;
}
