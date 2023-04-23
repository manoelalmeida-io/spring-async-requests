package sptech.school.asyncrequests.service.customer.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class CustomerExportXlsxDto {

  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private String agency;
  private String number;
  private String bank;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
}
