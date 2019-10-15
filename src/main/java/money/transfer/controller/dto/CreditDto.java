package money.transfer.controller.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder()
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreditDto {
  private UUID id;
  private BigDecimal creditAmount;
}
