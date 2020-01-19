package us.vicentini.services;

import us.vicentini.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> findAllCustomers();

    CustomerDTO findCustomersById(Long id);

    CustomerDTO createNewCustomer(CustomerDTO customer);
}
