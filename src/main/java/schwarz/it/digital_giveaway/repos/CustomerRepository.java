package schwarz.it.digital_giveaway.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import schwarz.it.digital_giveaway.domain.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
