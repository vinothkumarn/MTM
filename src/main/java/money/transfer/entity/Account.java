package money.transfer.entity;

import lombok.Data;
import java.util.UUID;
import java.math.BigDecimal;
import money.transfer.utils.ValidateName;

@Data
public class Account {

    private UUID id;

    private String accountOwnerName;

    private BigDecimal accountBalance;

    public void setAccountOwnerName(String accountOwnerName) {
        if(ValidateName.validate(accountOwnerName)) {
            this.accountOwnerName = accountOwnerName;
        } else {
            throw new IllegalStateException("Only alphabets and spaces are allowed");
        }
    }
}
