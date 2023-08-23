package schwarz.it.digital_giveaway.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("schwarz.it.digital_giveaway.domain")
@EnableJpaRepositories("schwarz.it.digital_giveaway.repos")
@EnableTransactionManagement
public class DomainConfig {
}
