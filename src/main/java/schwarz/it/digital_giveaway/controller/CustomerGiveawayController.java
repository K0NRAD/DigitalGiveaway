package schwarz.it.digital_giveaway.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import schwarz.it.digital_giveaway.domain.Customer;
import schwarz.it.digital_giveaway.domain.Giveaway;
import schwarz.it.digital_giveaway.model.CustomerGiveawayDTO;
import schwarz.it.digital_giveaway.repos.CustomerRepository;
import schwarz.it.digital_giveaway.repos.GiveawayRepository;
import schwarz.it.digital_giveaway.service.CustomerGiveawayService;
import schwarz.it.digital_giveaway.util.CustomCollectors;
import schwarz.it.digital_giveaway.util.WebUtils;


@Controller
@RequestMapping("/customerGiveaway")
public class CustomerGiveawayController {

    private final CustomerGiveawayService customerGiveawayService;
    private final CustomerRepository customerRepository;
    private final GiveawayRepository giveawayRepository;

    public CustomerGiveawayController(final CustomerGiveawayService customerGiveawayService,
                                      final CustomerRepository customerRepository,
                                      final GiveawayRepository giveawayRepository) {
        this.customerGiveawayService = customerGiveawayService;
        this.customerRepository = customerRepository;
        this.giveawayRepository = giveawayRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("customerValues", customerRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Customer::getId, Customer::getEmail)));
        model.addAttribute("giveawayValues", giveawayRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Giveaway::getId, Giveaway::getName)));
    }

    @GetMapping
    public String list(@RequestParam(value = "field", defaultValue = "date") String field,
                       @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder,
                       final Model model) {
        Sort sortBy = Sort.by(sortOrder.equals("asc") ? Sort.Order.desc(field) : Sort.Order.asc(field));
        model.addAttribute("customerGiveaways", customerGiveawayService.findAll(sortBy));
        model.addAttribute("sortOrder", sortOrder.equals("asc") ? "desc" : "asc");
        return "customerGiveaway/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("customerGiveaway") final CustomerGiveawayDTO customerGiveawayDTO) {
        return "customerGiveaway/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("customerGiveaway") @Valid final CustomerGiveawayDTO customerGiveawayDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "customerGiveaway/add";
        }
        customerGiveawayService.create(customerGiveawayDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("customerGiveaway.create.success"));
        return "redirect:/customerGiveaway";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("customerGiveaway", customerGiveawayService.get(id));
        return "customerGiveaway/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
                       @ModelAttribute("customerGiveaway") @Valid final CustomerGiveawayDTO customerGiveawayDTO,
                       final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "customerGiveaway/edit";
        }
        customerGiveawayService.update(id, customerGiveawayDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("customerGiveaway.update.success"));
        return "redirect:/customerGiveaway";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        customerGiveawayService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("customerGiveaway.delete.success"));
        return "redirect:/customerGiveaway";
    }

}
