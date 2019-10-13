package money.transfer.entity;

import lombok.Data;
import java.util.UUID;
import java.math.BigDecimal;

@Data
public class Account {

    private UUID id;

    private String accountOwnerName;

    private BigDecimal accountBalance;

}
