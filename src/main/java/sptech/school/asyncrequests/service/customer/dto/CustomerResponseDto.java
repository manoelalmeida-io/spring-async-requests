package sptech.school.asyncrequests.service.customer.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class CustomerResponseDto {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
  private AccountDto account;

  @Data
  public static class AccountDto {

    private String agency;
    private String number;
    private String bank;
    private OffsetDateTime createdAt;
    private AccountBalanceDto accountBalance;
  }

  @Data
  public static class AccountBalanceDto {

    private BigDecimal balance;
    private OffsetDateTime updatedAt;
  }
}
