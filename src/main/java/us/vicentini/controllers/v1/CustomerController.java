package us.vicentini.controllers.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import us.vicentini.api.v1.model.CustomerDTO;
import us.vicentini.api.v1.model.CustomerListDTO;
import us.vicentini.services.CustomerService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = "customer-controller")
@RestController
@RequestMapping(CustomerController.BASE_PATH)
@RequiredArgsConstructor
public class CustomerController {
    public static final String BASE_PATH = "/api/v1/customers";
    private final CustomerService customerService;


    @ApiOperation(value = "This will list all customers", notes = "These are some notes about the API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public CustomerListDTO findAllCustomers() {
        List<CustomerDTO> customers = customerService.findAllCustomers();
        return new CustomerListDTO(customers);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public CustomerDTO findCustomerById(@PathVariable Long id) {
        return customerService.findCustomersById(id);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.createNewCustomer(customerDTO);
    }


    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(id, customerDTO);
    }


    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public CustomerDTO patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.patchCustomer(id, customerDTO);
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }
}
