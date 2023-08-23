package schwarz.it.digital_giveaway.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import schwarz.it.digital_giveaway.domain.Vendor;
import schwarz.it.digital_giveaway.model.GiveawayDTO;
import schwarz.it.digital_giveaway.repos.VendorRepository;
import schwarz.it.digital_giveaway.service.GiveawayService;
import schwarz.it.digital_giveaway.util.CustomCollectors;
import schwarz.it.digital_giveaway.util.WebUtils;


@Controller
@RequestMapping("/giveaways")
public class GiveawayController {

    private final GiveawayService giveawayService;
    private final VendorRepository vendorRepository;

    public GiveawayController(final GiveawayService giveawayService,
            final VendorRepository vendorRepository) {
        this.giveawayService = giveawayService;
        this.vendorRepository = vendorRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("vendorValues", vendorRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Vendor::getId, Vendor::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("giveaways", giveawayService.findAll());
        return "giveaway/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("giveaway") final GiveawayDTO giveawayDTO) {
        return "giveaway/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("giveaway") @Valid final GiveawayDTO giveawayDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "giveaway/add";
        }
        giveawayService.create(giveawayDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("giveaway.create.success"));
        return "redirect:/giveaways";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("giveaway", giveawayService.get(id));
        return "giveaway/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("giveaway") @Valid final GiveawayDTO giveawayDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "giveaway/edit";
        }
        giveawayService.update(id, giveawayDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("giveaway.update.success"));
        return "redirect:/giveaways";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = giveawayService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            giveawayService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("giveaway.delete.success"));
        }
        return "redirect:/giveaways";
    }

}
