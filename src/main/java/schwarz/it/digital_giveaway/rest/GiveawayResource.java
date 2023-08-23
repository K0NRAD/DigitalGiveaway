package schwarz.it.digital_giveaway.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
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
import schwarz.it.digital_giveaway.model.GiveawayDTO;
import schwarz.it.digital_giveaway.service.GiveawayService;


@RestController
@RequestMapping(value = "/api/giveaways", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiveawayResource {

    private final GiveawayService giveawayService;

    public GiveawayResource(final GiveawayService giveawayService) {
        this.giveawayService = giveawayService;
    }

    @GetMapping
    public ResponseEntity<List<GiveawayDTO>> getAllGiveaways() {
        return ResponseEntity.ok(giveawayService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiveawayDTO> getGiveaway(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(giveawayService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createGiveaway(@RequestBody @Valid final GiveawayDTO giveawayDTO) {
        final Long createdId = giveawayService.create(giveawayDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateGiveaway(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final GiveawayDTO giveawayDTO) {
        giveawayService.update(id, giveawayDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteGiveaway(@PathVariable(name = "id") final Long id) {
        giveawayService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
