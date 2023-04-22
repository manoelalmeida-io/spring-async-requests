package sptech.school.asyncrequests.service.customer.dto;

import org.springframework.stereotype.Service;
import sptech.school.asyncrequests.domain.Customer;
import sptech.school.asyncrequests.domain.account.Account;
import sptech.school.asyncrequests.domain.account.AccountBalance;

@Service
public class CustomerMapper {

  public CustomerResponseDto toDto(Customer customer) {
    if (customer == null) {
      return null;
    }

    CustomerResponseDto customerResponseDto = new CustomerResponseDto();
    customerResponseDto.setId(customer.getId());
    customerResponseDto.setFirstName(customer.getFirstName());
    customerResponseDto.setLastName(customer.getLastName());
    customerResponseDto.setEmail(customer.getEmail());
    customerResponseDto.setPhone(customer.getPhone());
    customerResponseDto.setCreatedAt(customer.getCreatedAt());
    customerResponseDto.setUpdatedAt(customer.getUpdatedAt());

    if (customer.getAccount() != null) {
      customerResponseDto.setAccount(toAccountDto(customer.getAccount()));
    }

    return customerResponseDto;
  }

  public Customer toDomain(CustomerCreateRequestDto customerCreateRequestDto) {
    if (customerCreateRequestDto == null) {
      return null;
    }

    Customer customer = new Customer();
    customer.setFirstName(customerCreateRequestDto.getFirstName());
    customer.setLastName(customerCreateRequestDto.getLastName());
    customer.setEmail(customerCreateRequestDto.getEmail());
    customer.setPhone(customerCreateRequestDto.getPhone());
    return customer;
  }

  private CustomerResponseDto.AccountDto toAccountDto(Account account) {
    if (account == null) {
      return null;
    }

    CustomerResponseDto.AccountDto accountDto = new CustomerResponseDto.AccountDto();
    accountDto.setAgency(account.getAgency());
    accountDto.setNumber(account.getNumber());
    accountDto.setBank(account.getBank());
    accountDto.setCreatedAt(account.getCreatedAt());

    if (account.getAccountBalance() != null) {
      accountDto.setAccountBalance(toAccountBalanceDto(account.getAccountBalance()));
    }

    return accountDto;
  }

  private CustomerResponseDto.AccountBalanceDto toAccountBalanceDto(AccountBalance accountBalance) {
    if (accountBalance == null) {
      return null;
    }

    CustomerResponseDto.AccountBalanceDto accountBalanceDto = new CustomerResponseDto.AccountBalanceDto();
    accountBalanceDto.setBalance(accountBalance.getBalance());
    accountBalanceDto.setUpdatedAt(accountBalance.getUpdatedAt());
    return accountBalanceDto;
  }
}
