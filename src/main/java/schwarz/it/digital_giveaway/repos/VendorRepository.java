package schwarz.it.digital_giveaway.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import schwarz.it.digital_giveaway.domain.Vendor;


public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
