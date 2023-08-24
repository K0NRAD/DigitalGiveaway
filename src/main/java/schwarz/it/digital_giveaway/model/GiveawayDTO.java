package schwarz.it.digital_giveaway.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GiveawayDTO {

    private Long id;

    @NotNull
    @Size(max = 64)
    private String name;

    @Size(max = 256)
    private String description;

    @NotNull
    private Integer quantity;

    private Integer available;

    private Long vendor;

}
