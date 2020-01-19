package us.vicentini.api.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.vicentini.controllers.v1.CustomerController;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class CustomerDTO {
    @JsonIgnore
    private Long id;
    private String firstName;
    private String lastName;


    public String getCustomerUrl() {
        return CustomerController.BASE_PATH + "/" + getId();
    }
}
