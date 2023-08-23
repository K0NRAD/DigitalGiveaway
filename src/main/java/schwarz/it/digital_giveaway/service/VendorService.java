package schwarz.it.digital_giveaway.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import schwarz.it.digital_giveaway.domain.Giveaway;
import schwarz.it.digital_giveaway.domain.Vendor;
import schwarz.it.digital_giveaway.model.VendorDTO;
import schwarz.it.digital_giveaway.repos.GiveawayRepository;
import schwarz.it.digital_giveaway.repos.VendorRepository;
import schwarz.it.digital_giveaway.util.NotFoundException;
import schwarz.it.digital_giveaway.util.WebUtils;


@Service
public class VendorService {

    private final VendorRepository vendorRepository;
    private final GiveawayRepository giveawayRepository;

    public VendorService(final VendorRepository vendorRepository,
            final GiveawayRepository giveawayRepository) {
        this.vendorRepository = vendorRepository;
        this.giveawayRepository = giveawayRepository;
    }

    public List<VendorDTO> findAll() {
        final List<Vendor> vendors = vendorRepository.findAll(Sort.by("id"));
        return vendors.stream()
                .map(vendor -> mapToDTO(vendor, new VendorDTO()))
                .toList();
    }

    public VendorDTO get(final Long id) {
        return vendorRepository.findById(id)
                .map(vendor -> mapToDTO(vendor, new VendorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final VendorDTO vendorDTO) {
        final Vendor vendor = new Vendor();
        mapToEntity(vendorDTO, vendor);
        return vendorRepository.save(vendor).getId();
    }

    public void update(final Long id, final VendorDTO vendorDTO) {
        final Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(vendorDTO, vendor);
        vendorRepository.save(vendor);
    }

    public void delete(final Long id) {
        vendorRepository.deleteById(id);
    }

    private VendorDTO mapToDTO(final Vendor vendor, final VendorDTO vendorDTO) {
        vendorDTO.setId(vendor.getId());
        vendorDTO.setName(vendor.getName());
        vendorDTO.setZip(vendor.getZip());
        vendorDTO.setStreet(vendor.getStreet());
        vendorDTO.setPhone(vendor.getPhone());
        vendorDTO.setContact(vendor.getContact());
        return vendorDTO;
    }

    private Vendor mapToEntity(final VendorDTO vendorDTO, final Vendor vendor) {
        vendor.setName(vendorDTO.getName());
        vendor.setZip(vendorDTO.getZip());
        vendor.setStreet(vendorDTO.getStreet());
        vendor.setPhone(vendorDTO.getPhone());
        vendor.setContact(vendorDTO.getContact());
        return vendor;
    }

    public String getReferencedWarning(final Long id) {
        final Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Giveaway vendorGiveaway = giveawayRepository.findFirstByVendor(vendor);
        if (vendorGiveaway != null) {
            return WebUtils.getMessage("vendor.giveaway.vendor.referenced", vendorGiveaway.getId());
        }
        return null;
    }

}
