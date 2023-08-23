package schwarz.it.digital_giveaway.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Vendor {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(length = 256)
    private String name;

    @Column(length = 32)
    private String zip;

    @Column(length = 64)
    private String street;

    @Column(length = 128)
    private String phone;

    @Column(length = 128)
    private String contact;

    @OneToMany(mappedBy = "vendor")
    private Set<Giveaway> giveaways;

}
