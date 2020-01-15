package us.vicentini.api.v1.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static us.vicentini.controllers.v1.CustomerController.API_V1_CUSTOMERS;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerDTO {
    private Long id;
    private String firstName;
    private String lastName;


    public String getCustomerUrl() {
        return API_V1_CUSTOMERS + "/" + getId();
    }
}
