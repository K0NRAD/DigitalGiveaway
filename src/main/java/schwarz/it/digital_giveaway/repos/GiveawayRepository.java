package schwarz.it.digital_giveaway.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import schwarz.it.digital_giveaway.domain.Giveaway;
import schwarz.it.digital_giveaway.domain.Vendor;


public interface GiveawayRepository extends JpaRepository<Giveaway, Long> {

    Giveaway findFirstByVendor(Vendor vendor);

}
