package us.vicentini.services;

import us.vicentini.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> findAllCustomers();

    CustomerDTO findCustomersById(Long id);

    CustomerDTO createNewCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);

    CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);

    void deleteCustomerById(Long id);
}
