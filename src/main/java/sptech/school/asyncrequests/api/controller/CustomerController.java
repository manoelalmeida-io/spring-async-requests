package sptech.school.asyncrequests.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.asyncrequests.domain.Customer;
import sptech.school.asyncrequests.service.customer.CustomerService;
import sptech.school.asyncrequests.service.customer.dto.CustomerCreateRequestDto;
import sptech.school.asyncrequests.service.customer.dto.CustomerMapper;
import sptech.school.asyncrequests.service.customer.dto.CustomerResponseDto;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  private final CustomerMapper customerMapper;

  @PostMapping
  public ResponseEntity<CustomerResponseDto> post(@RequestBody @Valid CustomerCreateRequestDto request) {
    Customer customer = this.customerMapper.toDomain(request);
    Customer registeredCustomer = this.customerService.post(customer);
    return ResponseEntity.ok(this.customerMapper.toDto(registeredCustomer));
  }
}
