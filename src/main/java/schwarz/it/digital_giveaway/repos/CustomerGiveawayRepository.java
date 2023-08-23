package schwarz.it.digital_giveaway.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import schwarz.it.digital_giveaway.domain.Customer;
import schwarz.it.digital_giveaway.domain.CustomerGiveaway;
import schwarz.it.digital_giveaway.domain.Giveaway;


public interface CustomerGiveawayRepository extends JpaRepository<CustomerGiveaway, Long> {

    CustomerGiveaway findFirstByGiveaway(Giveaway giveaway);

    CustomerGiveaway findFirstByCustomer(Customer customer);

}
