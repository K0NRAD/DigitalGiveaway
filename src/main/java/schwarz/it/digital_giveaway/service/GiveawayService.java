package schwarz.it.digital_giveaway.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import schwarz.it.digital_giveaway.domain.CustomerGiveaway;
import schwarz.it.digital_giveaway.domain.Giveaway;
import schwarz.it.digital_giveaway.domain.Vendor;
import schwarz.it.digital_giveaway.model.GiveawayDTO;
import schwarz.it.digital_giveaway.repos.CustomerGiveawayRepository;
import schwarz.it.digital_giveaway.repos.GiveawayRepository;
import schwarz.it.digital_giveaway.repos.VendorRepository;
import schwarz.it.digital_giveaway.util.NotFoundException;
import schwarz.it.digital_giveaway.util.WebUtils;


@Service
public class GiveawayService {

    private final GiveawayRepository giveawayRepository;
    private final VendorRepository vendorRepository;
    private final CustomerGiveawayRepository customerGiveawayRepository;

    public GiveawayService(final GiveawayRepository giveawayRepository,
            final VendorRepository vendorRepository,
            final CustomerGiveawayRepository customerGiveawayRepository) {
        this.giveawayRepository = giveawayRepository;
        this.vendorRepository = vendorRepository;
        this.customerGiveawayRepository = customerGiveawayRepository;
    }

    public List<GiveawayDTO> findAll() {
        final List<Giveaway> giveaways = giveawayRepository.findAll(Sort.by("id"));
        return giveaways.stream()
                .map(giveaway -> mapToDTO(giveaway, new GiveawayDTO()))
                .toList();
    }

    public GiveawayDTO get(final Long id) {
        return giveawayRepository.findById(id)
                .map(giveaway -> mapToDTO(giveaway, new GiveawayDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final GiveawayDTO giveawayDTO) {
        final Giveaway giveaway = new Giveaway();
        mapToEntity(giveawayDTO, giveaway);
        return giveawayRepository.save(giveaway).getId();
    }

    public void update(final Long id, final GiveawayDTO giveawayDTO) {
        final Giveaway giveaway = giveawayRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(giveawayDTO, giveaway);
        giveawayRepository.save(giveaway);
    }

    public void delete(final Long id) {
        giveawayRepository.deleteById(id);
    }

    private GiveawayDTO mapToDTO(final Giveaway giveaway, final GiveawayDTO giveawayDTO) {
        giveawayDTO.setId(giveaway.getId());
        giveawayDTO.setName(giveaway.getName());
        giveawayDTO.setDescription(giveaway.getDescription());
        giveawayDTO.setNote(giveaway.getNote());
        giveawayDTO.setType(giveaway.getType());
        giveawayDTO.setSize(giveaway.getSize());
        giveawayDTO.setQuantity(giveaway.getQuantity());
        giveawayDTO.setVendor(giveaway.getVendor() == null ? null : giveaway.getVendor().getId());
        return giveawayDTO;
    }

    private Giveaway mapToEntity(final GiveawayDTO giveawayDTO, final Giveaway giveaway) {
        giveaway.setName(giveawayDTO.getName());
        giveaway.setDescription(giveawayDTO.getDescription());
        giveaway.setNote(giveawayDTO.getNote());
        giveaway.setType(giveawayDTO.getType());
        giveaway.setSize(giveawayDTO.getSize());
        giveaway.setQuantity(giveawayDTO.getQuantity());
        final Vendor vendor = giveawayDTO.getVendor() == null ? null : vendorRepository.findById(giveawayDTO.getVendor())
                .orElseThrow(() -> new NotFoundException("vendor not found"));
        giveaway.setVendor(vendor);
        return giveaway;
    }

    public String getReferencedWarning(final Long id) {
        final Giveaway giveaway = giveawayRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final CustomerGiveaway giveawaysCustomerGiveaway = customerGiveawayRepository.findFirstByGiveaway(giveaway);
        if (giveawaysCustomerGiveaway != null) {
            return WebUtils.getMessage("giveaway.customerGiveaway.giveaways.referenced", giveawaysCustomerGiveaway.getId());
        }
        return null;
    }

}
