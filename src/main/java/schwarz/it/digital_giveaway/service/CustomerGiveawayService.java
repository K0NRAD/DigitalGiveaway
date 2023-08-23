package schwarz.it.digital_giveaway.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import schwarz.it.digital_giveaway.domain.Customer;
import schwarz.it.digital_giveaway.domain.CustomerGiveaway;
import schwarz.it.digital_giveaway.domain.Giveaway;
import schwarz.it.digital_giveaway.model.CustomerGiveawayDTO;
import schwarz.it.digital_giveaway.repos.CustomerGiveawayRepository;
import schwarz.it.digital_giveaway.repos.CustomerRepository;
import schwarz.it.digital_giveaway.repos.GiveawayRepository;
import schwarz.it.digital_giveaway.util.NotFoundException;

import java.util.List;


@Service
public class CustomerGiveawayService {

    private final CustomerGiveawayRepository customerGiveawayRepository;
    private final CustomerRepository customerRepository;
    private final GiveawayRepository giveawayRepository;

    public CustomerGiveawayService(final CustomerGiveawayRepository customerGiveawayRepository,
                                   final CustomerRepository customerRepository,
                                   final GiveawayRepository giveawayRepository) {
        this.customerGiveawayRepository = customerGiveawayRepository;
        this.customerRepository = customerRepository;
        this.giveawayRepository = giveawayRepository;
    }

    public List<CustomerGiveawayDTO> findAll(Sort sortBy) {
        final List<CustomerGiveaway> customerGiveaways = customerGiveawayRepository.findAll(sortBy);
        return customerGiveaways.stream()
                .map(customerGiveaway -> mapToDTO(customerGiveaway, new CustomerGiveawayDTO()))
                .toList();
    }

    public CustomerGiveawayDTO get(final Long id) {
        return customerGiveawayRepository.findById(id)
                .map(customerGiveaway -> mapToDTO(customerGiveaway, new CustomerGiveawayDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CustomerGiveawayDTO customerGiveawayDTO) {
        final CustomerGiveaway customerGiveaway = new CustomerGiveaway();
        mapToEntity(customerGiveawayDTO, customerGiveaway);
        return customerGiveawayRepository.save(customerGiveaway).getId();
    }

    public void update(final Long id, final CustomerGiveawayDTO customerGiveawayDTO) {
        final CustomerGiveaway customerGiveaway = customerGiveawayRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(customerGiveawayDTO, customerGiveaway);
        customerGiveawayRepository.save(customerGiveaway);
    }

    public void delete(final Long id) {
        customerGiveawayRepository.deleteById(id);
    }

    private CustomerGiveawayDTO mapToDTO(final CustomerGiveaway customerGiveaway,
                                         final CustomerGiveawayDTO customerGiveawayDTO) {
        customerGiveawayDTO.setId(customerGiveaway.getId());
        customerGiveawayDTO.setDate(customerGiveaway.getDate());
        customerGiveawayDTO.setQuantity(customerGiveaway.getQuantity());

        Customer customer = customerGiveaway.getCustomer();
        if (customer != null) {
            customerGiveawayDTO.setCustomer(customer.getId());
            customerGiveawayDTO.setCustomerEmail(customer.getEmail());
        }

        Giveaway giveaway = customerGiveaway.getGiveaway();
        if(giveaway != null) {
            customerGiveawayDTO.setGiveaway(giveaway.getId());
            customerGiveawayDTO.setGiveawayName(giveaway.getName());
        }
        return customerGiveawayDTO;
    }

    private CustomerGiveaway mapToEntity(final CustomerGiveawayDTO customerGiveawayDTO,
                                         final CustomerGiveaway customerGiveaway) {
        customerGiveaway.setDate(customerGiveawayDTO.getDate());
        customerGiveaway.setQuantity(customerGiveawayDTO.getQuantity());
        final Customer customers = customerGiveawayDTO.getCustomer() == null ? null : customerRepository.findById(customerGiveawayDTO.getCustomer())
                .orElseThrow(() -> new NotFoundException("customers not found"));
        customerGiveaway.setCustomer(customers);
        final Giveaway giveaways = customerGiveawayDTO.getGiveaway() == null ? null : giveawayRepository.findById(customerGiveawayDTO.getGiveaway())
                .orElseThrow(() -> new NotFoundException("giveaways not found"));
        customerGiveaway.setGiveaway(giveaways);
        return customerGiveaway;
    }

}
