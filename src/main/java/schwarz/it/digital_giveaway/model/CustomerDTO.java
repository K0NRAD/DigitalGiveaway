package schwarz.it.digital_giveaway.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CustomerDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String email;

    @Size(max = 256)
    private String notes;

    private String giveaways;
}
