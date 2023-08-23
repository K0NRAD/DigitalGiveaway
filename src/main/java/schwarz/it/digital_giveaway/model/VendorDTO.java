package schwarz.it.digital_giveaway.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VendorDTO {

    private Long id;

    @Size(max = 256)
    private String name;

    @Size(max = 32)
    private String zip;

    @Size(max = 64)
    private String street;

    @Size(max = 128)
    private String phone;

    @Size(max = 128)
    private String contact;

}
