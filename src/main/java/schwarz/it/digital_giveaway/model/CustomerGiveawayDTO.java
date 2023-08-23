package schwarz.it.digital_giveaway.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class CustomerGiveawayDTO {

    private Long id;

    @NotNull
    private LocalDate date;

    @NotNull
    private Integer quantity = 1;

    @NotNull
    private Long customer;

    private String customerEmail;

    @NotNull
    private Long giveaway;

    private String giveawayName;

}
