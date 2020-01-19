package us.vicentini.controllers.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import us.vicentini.api.v1.model.CustomerDTO;
import us.vicentini.api.v1.model.CustomerListDTO;
import us.vicentini.services.CustomerService;

import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(CustomerController.BASE_PATH)
@RequiredArgsConstructor
public class CustomerController {
    public static final String BASE_PATH = "/api/v1/customers";
    private final CustomerService customerService;


    @ResponseBody
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerListDTO> findAllCustomers() {
        List<CustomerDTO> customers = customerService.findAllCustomers();
        return ResponseEntity.ok(new CustomerListDTO(customers));
    }


    @ResponseBody
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable Long id) {
        CustomerDTO customer = customerService.findCustomersById(id);
        return ResponseEntity.ok(customer);
    }


    @ResponseBody
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO customer = customerService.createNewCustomer(customerDTO);
        return ResponseEntity.created(URI.create(customer.getCustomerUrl())).body(customer);
    }


    @ResponseBody
    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO customer = customerService.updateCustomer(id, customerDTO);
        return ResponseEntity.ok().body(customer);
    }


    @ResponseBody
    @PatchMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO customer = customerService.patchCustomer(id, customerDTO);
        return ResponseEntity.ok().body(customer);
    }


    @ResponseBody
    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok().build();
    }
}
