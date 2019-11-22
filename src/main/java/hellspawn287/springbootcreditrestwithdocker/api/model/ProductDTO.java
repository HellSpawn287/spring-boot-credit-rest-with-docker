package hellspawn287.springbootcreditrestwithdocker.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String name;
    private Long value;

    @JsonProperty("product_url")
    private String productURL;
}
