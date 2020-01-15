package us.vicentini.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import us.vicentini.api.v1.mapper.CustomerMapper;
import us.vicentini.api.v1.model.CustomerDTO;
import us.vicentini.domain.Customer;
import us.vicentini.repositories.CustomerRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    private static final long ID = 1L;
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";

    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;
    @Mock
    private CustomerRepository customerRepository;
    private CustomerService customerService;


    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(customerMapper, customerRepository);
    }


    @Test
    void getAllCustomers() {
        Customer customer = createCustomer();
        when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));

        List<CustomerDTO> customers = customerService.findAllCustomers();

        assertNotNull(customers);
        assertEquals(1, customers.size());
    }


    @Test
    void findCustomerById() {
        Customer customer = createCustomer();
        when(customerRepository.findById(ID)).thenReturn(Optional.of(customer));

        CustomerDTO customerDto = customerService.findCustomersById(ID);

        assertEquals(ID, customerDto.getId());
        assertEquals(FIRST_NAME, customerDto.getFirstName());
        assertEquals(LAST_NAME, customerDto.getLastName());
    }


    @Test
    void shouldFailWithExceptionCustomerNotFound() {
        assertThrows(RuntimeException.class, () -> customerService.findCustomersById(ID));
    }


    private Customer createCustomer() {
        return Customer.builder()
                .id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
    }
}