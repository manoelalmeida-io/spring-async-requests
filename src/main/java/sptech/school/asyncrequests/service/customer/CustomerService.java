package sptech.school.asyncrequests.service.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sptech.school.asyncrequests.api.configuration.BankConfiguration;
import sptech.school.asyncrequests.domain.Customer;
import sptech.school.asyncrequests.domain.account.Account;
import sptech.school.asyncrequests.domain.account.AccountBalance;
import sptech.school.asyncrequests.domain.account.data.AccountBalanceRepository;
import sptech.school.asyncrequests.domain.account.data.AccountRepository;
import sptech.school.asyncrequests.domain.data.CustomerRepository;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

  private final BankConfiguration bankConfiguration;

  private final CustomerRepository customerRepository;
  private final AccountRepository accountRepository;
  private final AccountBalanceRepository accountBalanceRepository;

  @Transactional
  public Customer post(Customer customer) {
    Customer registeredCustomer = this.customerRepository.save(customer);

    Account account = new Account();
    account.setAgency(bankConfiguration.getAgency());
    account.setNumber(generateAccountNumber());
    account.setBank(bankConfiguration.getBankNumber());
    account.setCustomer(registeredCustomer);

    Account registeredAccount = this.accountRepository.save(account);

    AccountBalance accountBalance = new AccountBalance();
    accountBalance.setBalance(BigDecimal.ZERO);
    accountBalance.setAccount(registeredAccount);

    AccountBalance registeredAccountBalance = this.accountBalanceRepository.save(accountBalance);

    registeredAccount.setAccountBalance(registeredAccountBalance);
    registeredCustomer.setAccount(registeredAccount);

    log.info("Customer registered, id: {}", registeredCustomer.getId());

    return registeredCustomer;
  }

  private String generateAccountNumber() {
    Optional<Account> lastCreatedAccount = this.accountRepository.findFirstByOrderByIdDesc();

    long lastAccountNumberLong = lastCreatedAccount.map(
        account -> Long.parseLong(removeVerifier(account.getNumber()))).orElse(0L);

    NumberFormat formatter = new DecimalFormat("000000000");
    String accountNumber = formatter.format(lastAccountNumberLong + 1);

    return accountNumber + generateVerifier(accountNumber);
  }

  public String generateVerifier(String accountNumber) {
    int[] weights = {10, 9, 8, 7, 6, 5, 4, 3, 2};

    int sum = 0;
    for (int i = 0; i < weights.length; i++) {
      int digit = Integer.parseInt(accountNumber.substring(i, i + 1));
      sum += digit * weights[i];
    }

    int remainder = sum % 11;
    int verifier = (11 - remainder) % 10;

    return Integer.toString(verifier);
  }

  public String removeVerifier(String accountNumber) {
    return accountNumber.substring(0, accountNumber.length() - 1);
  }
}
