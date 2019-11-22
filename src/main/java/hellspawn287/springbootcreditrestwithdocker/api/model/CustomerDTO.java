package hellspawn287.springbootcreditrestwithdocker.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @PESEL
    @Transient
    private String peselNumber;

    @Past
    private Date dateOfBirth;

    @JsonProperty("customer_url")
    private String customerURL;
}