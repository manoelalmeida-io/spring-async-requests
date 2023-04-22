package sptech.school.asyncrequests.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AsyncConfiguration {

  @Bean
  ExecutorService singleThreadExecutor() {
    return Executors.newFixedThreadPool(1);
  }
}
