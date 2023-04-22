package sptech.school.asyncrequests.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ClockConfiguration {

  @Bean
  Clock clock() {
    return Clock.systemDefaultZone();
  }
}
