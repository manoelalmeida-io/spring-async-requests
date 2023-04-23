package sptech.school.asyncrequests.api.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import sptech.school.asyncrequests.domain.Customer;
import sptech.school.asyncrequests.service.customer.CustomerExportService;
import sptech.school.asyncrequests.service.customer.CustomerService;
import sptech.school.asyncrequests.service.customer.dto.CustomerCreateRequestDto;
import sptech.school.asyncrequests.service.customer.dto.CustomerMapper;
import sptech.school.asyncrequests.service.customer.dto.CustomerResponseDto;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Slf4j
@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;
  private final CustomerExportService customerExportService;

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

  @PostMapping("/reports")
  public ResponseEntity<Void> createCustomersReport() throws IOException {
    this.customerExportService.writeFileToOutputStream();
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/reports/download")
  public StreamingResponseBody export(HttpServletResponse response) throws IOException {
    Path filePath = Paths.get("customers.xlsx");

    InputStream is = Files.newInputStream(filePath);

    response.setContentLength((int) filePath.toFile().length());
    response.setHeader("Content-Disposition", "attachment; filename=" + "customers.xlsx");

    return outputStream -> {

      int nRead;
      byte[] data = new byte[1024];
      while ((nRead = is.read(data)) != -1) {
        outputStream.write(data, 0, nRead);

        // Simulate slow network
        sleep();
      }

      is.close();
    };
  }

  private void sleep() {
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
