package hellspawn287.springbootcreditrestwithdocker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please enter your first name")
    private String firstname;
    @NotBlank(message = "Please enter your last name")
    private String lastname;

    @PESEL(message = "Please enter your PESEL number")
    @Transient
    private String peselNumber;

//    @Past
//    private Date dateOfBirth;

    public Customer(@NotBlank String firstname, @NotBlank String lastname, @PESEL String peselNumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.peselNumber = peselNumber;
    }
}
