package money.transfer.entity;



import java.util.UUID;
import java.math.BigDecimal;

import lombok.Data;
import lombok.Builder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@Builder
@Table(name = "account")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {

  @Id
  private UUID id;

  @Column(nullable = false)
  private String accountOwnerName;

  @Column(nullable = false)
  private BigDecimal accountBalance;

}
