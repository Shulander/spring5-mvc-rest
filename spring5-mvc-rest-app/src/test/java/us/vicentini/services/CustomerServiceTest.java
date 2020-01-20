package us.vicentini.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import us.vicentini.api.v1.mapper.CustomerMapper;
import us.vicentini.controllers.v1.CustomerController;
import us.vicentini.domain.Customer;
import us.vicentini.exceptions.ResourceNotFoundException;
import us.vicentini.model.CustomerDTO;
import us.vicentini.repositories.CustomerRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
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

        assertEquals(FIRST_NAME, customerDto.getFirstName());
        assertEquals(LAST_NAME, customerDto.getLastName());
        assertEquals(CustomerController.BASE_PATH + "/" + ID, customerDto.getCustomerUrl());
    }


    @Test
    void shouldNotFindCustomerById() {
        when(customerRepository.findById(ID)).thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class, () -> customerService.findCustomersById(ID));

        assertEquals("Customer not found: 1", ex.getMessage());
    }


    @Test
    void shouldFailWithExceptionCustomerNotFound() {
        when(customerRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> customerService.findCustomersById(ID));
    }


    @Test
    void shouldSaveNewCustomer() {
        CustomerDTO customerDTO = createCustomerDTO();
        Customer customer = createCustomer();
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO persistedCustomer = customerService.createNewCustomer(customerDTO);

        assertNotNull(persistedCustomer);
        assertEquals(FIRST_NAME, persistedCustomer.getFirstName());
        assertEquals(LAST_NAME, persistedCustomer.getLastName());
        assertEquals(CustomerController.BASE_PATH + "/" + ID, persistedCustomer.getCustomerUrl());
    }


    @Test
    void shouldUpdateCustomer() {
        CustomerDTO customerDTO = createCustomerDTO();
        Customer customer = createCustomer();
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO persistedCustomer = customerService.updateCustomer(ID, customerDTO);

        assertNotNull(persistedCustomer);
        assertEquals(FIRST_NAME, persistedCustomer.getFirstName());
        assertEquals(LAST_NAME, persistedCustomer.getLastName());
        assertEquals(CustomerController.BASE_PATH + "/" + ID, persistedCustomer.getCustomerUrl());
    }


    @Test
    void shouldPatchCustomer() {
        CustomerDTO customerDTO = spy(createCustomerDTO());
        Customer customer = spy(Customer.builder().id(ID).build());
        Customer patchedCustomer = createCustomer();
        when(customerRepository.findById(ID)).thenReturn(Optional.of(customer));
        when(customerRepository.save(eq(patchedCustomer))).thenReturn(patchedCustomer);

        CustomerDTO persistedCustomer = customerService.patchCustomer(ID, customerDTO);

        assertNotNull(persistedCustomer);
        assertEquals(FIRST_NAME, persistedCustomer.getFirstName());
        assertEquals(LAST_NAME, persistedCustomer.getLastName());
        assertEquals(CustomerController.BASE_PATH + "/" + ID, persistedCustomer.getCustomerUrl());
        verify(customerDTO).getFirstName();
        verify(customer).setFirstName(FIRST_NAME);
        verify(customerDTO).getLastName();
        verify(customer).setLastName(LAST_NAME);
    }


    @Test
    void shouldNotPatchCustomerNotFound() {
        CustomerDTO customerDTO = spy(createCustomerDTO());
        when(customerRepository.findById(ID)).thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class, () -> customerService.patchCustomer(ID, customerDTO));

        assertEquals("Customer not found: 1", ex.getMessage());
    }


    @Test
    void shouldDeleteCustomerById() {
        customerService.deleteCustomerById(ID);

        verify(customerRepository).deleteById(ID);
    }


    private CustomerDTO createCustomerDTO() {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);
        return customer;
    }


    private Customer createCustomer() {
        return Customer.builder()
                .id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
    }


    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(customerRepository);
    }
}
