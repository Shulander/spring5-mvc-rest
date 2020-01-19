package us.vicentini.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import us.vicentini.api.v1.mapper.CustomerMapper;
import us.vicentini.api.v1.model.CustomerDTO;
import us.vicentini.domain.Customer;
import us.vicentini.exceptions.ResourceNotFoundException;
import us.vicentini.repositories.CustomerRepository;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
class CustomerServiceImpl implements CustomerService {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;


    @Override
    public List<CustomerDTO> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }


    @Override
    public CustomerDTO findCustomersById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
    }


    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.customerToCustomerDTO(savedCustomer);
    }


    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.customerToCustomerDTO(savedCustomer);
    }


    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id)
                .map(customer -> {
                    copyIfNonNull(customerDTO::getFirstName, customer::setFirstName);
                    copyIfNonNull(customerDTO::getLastName, customer::setLastName);
                    return customer;
                })
                .map(customerRepository::save)
                .map(customerMapper::customerToCustomerDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
    }


    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }


    private <T> void copyIfNonNull(Supplier<T> s, Consumer<T> c) {
        T value = s.get();
        if (value != null) {
            c.accept(value);
        }
    }

}
