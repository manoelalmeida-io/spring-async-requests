package sptech.school.asyncrequests.api.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.data.auditing.DateTimeProvider;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@RequiredArgsConstructor
public class OffsetDateTimeProvider implements DateTimeProvider {

  private final Clock clock;

  @Override
  public Optional<TemporalAccessor> getNow() {
    return Optional.of(OffsetDateTime.now(clock));
  }
}
