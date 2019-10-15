package money.transfer.controller.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder()
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferDto {
    private UUID senderId;
    private UUID receiverId;
    private BigDecimal amount;
}
