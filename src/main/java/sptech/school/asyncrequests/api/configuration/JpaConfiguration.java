package sptech.school.asyncrequests.api.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import sptech.school.asyncrequests.api.provider.OffsetDateTimeProvider;

import java.time.Clock;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@RequiredArgsConstructor
public class JpaConfiguration {

  private final Clock clock;

  @Bean
  DateTimeProvider dateTimeProvider() {
    return new OffsetDateTimeProvider(clock);
  }
}
