package us.vicentini.controllers.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import us.vicentini.api.v1.model.CustomerDTO;
import us.vicentini.api.v1.model.CustomerListDTO;
import us.vicentini.services.CustomerService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(CustomerController.API_V1_CUSTOMERS)
@RequiredArgsConstructor
public class CustomerController {
    public static final String API_V1_CUSTOMERS = "/api/v1/customers";
    private final CustomerService customerService;


    @ResponseBody
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerListDTO> listCategories() {
        List<CustomerDTO> customers = customerService.findAllCustomers();
        return ResponseEntity.ok(new CustomerListDTO(customers));
    }


    @ResponseBody
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> listCategories(@PathVariable Long id) {
        CustomerDTO customer = customerService.findCustomersById(id);
        return ResponseEntity.ok(customer);
    }
}
