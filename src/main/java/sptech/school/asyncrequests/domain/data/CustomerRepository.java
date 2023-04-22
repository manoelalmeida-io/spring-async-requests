package sptech.school.asyncrequests.domain.data;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.asyncrequests.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
