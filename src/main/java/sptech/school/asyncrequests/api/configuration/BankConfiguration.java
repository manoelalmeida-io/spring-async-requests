package sptech.school.asyncrequests.api.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@PropertySource("classpath:bank.properties")
public class BankConfiguration {

  @Value("${bank.agency}")
  private String agency;

  @Value("${bank.number}")
  private String bankNumber;
}
