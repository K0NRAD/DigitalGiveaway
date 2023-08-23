package schwarz.it.digital_giveaway.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import schwarz.it.digital_giveaway.model.VendorDTO;
import schwarz.it.digital_giveaway.service.VendorService;
import schwarz.it.digital_giveaway.util.WebUtils;


@Controller
@RequestMapping("/vendors")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(final VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("vendors", vendorService.findAll());
        return "vendor/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("vendor") final VendorDTO vendorDTO) {
        return "vendor/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("vendor") @Valid final VendorDTO vendorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "vendor/add";
        }
        vendorService.create(vendorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("vendor.create.success"));
        return "redirect:/vendors";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("vendor", vendorService.get(id));
        return "vendor/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("vendor") @Valid final VendorDTO vendorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "vendor/edit";
        }
        vendorService.update(id, vendorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("vendor.update.success"));
        return "redirect:/vendors";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = vendorService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            vendorService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("vendor.delete.success"));
        }
        return "redirect:/vendors";
    }

}
