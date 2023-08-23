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

    @Size(max = 256)
    private String note;

    @NotNull
    @Size(max = 32)
    private String type;

    @Size(max = 32)
    private String size;

    @NotNull
    private Integer quantity;

    private Long vendor;

}
