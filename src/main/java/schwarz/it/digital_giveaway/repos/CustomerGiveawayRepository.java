package schwarz.it.digital_giveaway.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import schwarz.it.digital_giveaway.domain.Customer;
import schwarz.it.digital_giveaway.domain.CustomerGiveaway;
import schwarz.it.digital_giveaway.domain.Giveaway;

import java.util.List;


public interface CustomerGiveawayRepository extends JpaRepository<CustomerGiveaway, Long> {

    CustomerGiveaway findFirstByGiveaway(Giveaway giveaway);

    CustomerGiveaway findFirstByCustomer(Customer customer);

    List<CustomerGiveaway> findByGiveaway(Giveaway giveaway);

    List<CustomerGiveaway> findByCustomer(Customer customer);
}
