package sptech.school.asyncrequests.domain.account.data;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.asyncrequests.domain.account.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

  Optional<Account> findFirstByOrderByIdDesc();
}
