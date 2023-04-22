package sptech.school.asyncrequests.domain.account.data;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.asyncrequests.domain.account.AccountBalance;

public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Long> {
}
