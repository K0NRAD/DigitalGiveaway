package schwarz.it.digital_giveaway.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import schwarz.it.digital_giveaway.model.CustomerGiveawayDTO;
import schwarz.it.digital_giveaway.service.CustomerGiveawayService;


@RestController
@RequestMapping(value = "/api/customerGiveaways", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerGiveawayResource {

    private final CustomerGiveawayService customerGiveawayService;

    public CustomerGiveawayResource(final CustomerGiveawayService customerGiveawayService) {
        this.customerGiveawayService = customerGiveawayService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerGiveawayDTO>> getAllCustomerGiveaways() {
        return ResponseEntity.ok(customerGiveawayService.findAll(Sort.by("date")));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerGiveawayDTO> getCustomerGiveaway(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(customerGiveawayService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCustomerGiveaway(
            @RequestBody @Valid final CustomerGiveawayDTO customerGiveawayDTO) {
        final Long createdId = customerGiveawayService.create(customerGiveawayDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateCustomerGiveaway(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final CustomerGiveawayDTO customerGiveawayDTO) {
        customerGiveawayService.update(id, customerGiveawayDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCustomerGiveaway(@PathVariable(name = "id") final Long id) {
        customerGiveawayService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
