package schwarz.it.digital_giveaway.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import schwarz.it.digital_giveaway.domain.Customer;
import schwarz.it.digital_giveaway.domain.CustomerGiveaway;
import schwarz.it.digital_giveaway.model.CustomerDTO;
import schwarz.it.digital_giveaway.repos.CustomerGiveawayRepository;
import schwarz.it.digital_giveaway.repos.CustomerRepository;
import schwarz.it.digital_giveaway.util.NotFoundException;
import schwarz.it.digital_giveaway.util.WebUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerGiveawayRepository customerGiveawayRepository;

    public CustomerService(final CustomerRepository customerRepository,
                           final CustomerGiveawayRepository customerGiveawayRepository) {
        this.customerRepository = customerRepository;
        this.customerGiveawayRepository = customerGiveawayRepository;
    }

    public List<CustomerDTO> findAll() {
        final List<Customer> customers = customerRepository.findAll(Sort.by("id"));
        return customers.stream()
                .map(customer -> mapToDTO(customer, new CustomerDTO()))
                .toList();
    }

    public CustomerDTO get(final Long id) {
        return customerRepository.findById(id)
                .map(customer -> mapToDTO(customer, new CustomerDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CustomerDTO customerDTO) {
        final Customer customer = new Customer();
        mapToEntity(customerDTO, customer);
        return customerRepository.save(customer).getId();
    }

    public void update(final Long id, final CustomerDTO customerDTO) {
        final Customer customer = customerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(customerDTO, customer);
        customerRepository.save(customer);
    }

    public void delete(final Long id) {
        customerRepository.deleteById(id);
    }

    private CustomerDTO mapToDTO(final Customer customer, final CustomerDTO customerDTO) {
        customerDTO.setId(customer.getId());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setNotes(customer.getNotes());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        List<String> giveaways = customerGiveawayRepository.findByCustomer(customer).stream()
                .map(customerGiveaway -> String.format("%s - %d x %s",
                        customerGiveaway.getDate().format(formatter),
                        customerGiveaway.getQuantity(),
                        customerGiveaway.getGiveaway().getName()))
                .toList();
        customerDTO.setGiveaways(String.join("\n", giveaways));
        return customerDTO;
    }

    private Customer mapToEntity(final CustomerDTO customerDTO, final Customer customer) {
        customer.setEmail(customerDTO.getEmail());
        customer.setNotes(customerDTO.getNotes());
        return customer;
    }

    public String getReferencedWarning(final Long id) {
        final Customer customer = customerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final CustomerGiveaway customersCustomerGiveaway = customerGiveawayRepository.findFirstByCustomer(customer);
        if (customersCustomerGiveaway != null) {
            return WebUtils.getMessage("customer.customerGiveaway.customers.referenced", customersCustomerGiveaway.getId());
        }
        return null;
    }

}
