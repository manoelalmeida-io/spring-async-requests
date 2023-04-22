package sptech.school.asyncrequests.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import sptech.school.asyncrequests.domain.Customer;
import sptech.school.asyncrequests.service.customer.CustomerService;
import sptech.school.asyncrequests.service.customer.dto.CustomerCreateRequestDto;
import sptech.school.asyncrequests.service.customer.dto.CustomerMapper;
import sptech.school.asyncrequests.service.customer.dto.CustomerResponseDto;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Slf4j
@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  private final ExecutorService singleThreadExecutor;

  private final CustomerMapper customerMapper;

  @PostMapping
  public DeferredResult<ResponseEntity<CustomerResponseDto>> post(@RequestBody @Valid CustomerCreateRequestDto request) {
    DeferredResult<ResponseEntity<CustomerResponseDto>> deferredResult = new DeferredResult<>();

    log.info("Request received: {}", request);

    Customer customer = this.customerMapper.toDomain(request);

    CompletableFuture.supplyAsync(() -> this.customerService.post(customer), singleThreadExecutor)
        .thenApply(registered -> ResponseEntity.ok(this.customerMapper.toDto(registered)))
        .whenComplete((response, throwable) -> {
          if (throwable != null) {
            log.error("Error while processing request", throwable);
            deferredResult.setErrorResult(throwable);
          } else {
            log.info("Request processed successfully");
            deferredResult.setResult(response);
          }
        });

    return deferredResult;
  }
}
