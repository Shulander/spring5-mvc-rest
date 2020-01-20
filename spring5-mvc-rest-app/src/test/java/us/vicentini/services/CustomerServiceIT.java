package us.vicentini.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import us.vicentini.api.v1.mapper.CustomerMapper;
import us.vicentini.api.v1.model.CustomerDTO;
import us.vicentini.bootstrap.Bootstrap;
import us.vicentini.repositories.CategoryRepository;
import us.vicentini.repositories.CustomerRepository;
import us.vicentini.repositories.VendorRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@ExtendWith(SpringExtension.class)
@DataJpaTest
class CustomerServiceIT {

    @Autowired
    private CustomerRepository customerRepository;
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private VendorRepository vendorRepository;

    private CustomerService customerService;


    @BeforeEach
    void setUp() {
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }


    @Test
    void patchCustomerFirstName() {
        CustomerDTO customerDto = CustomerDTO.builder()
                .firstName("Bat")
                .build();

        CustomerDTO originalCustomer = customerService.findCustomersById(getIdFirstCustomer());

        CustomerDTO patchedCustomer = customerService.patchCustomer(getIdFirstCustomer(), customerDto);

        assertNotNull(patchedCustomer);
        assertEquals("Bat", patchedCustomer.getFirstName());
        assertEquals(originalCustomer.getLastName(), patchedCustomer.getLastName());
    }


    @Test
    void patchCustomerLastName() {
        CustomerDTO customerDto = CustomerDTO.builder()
                .lastName("Man")
                .build();

        CustomerDTO originalCustomer = customerService.findCustomersById(getIdFirstCustomer());

        CustomerDTO patchedCustomer = customerService.patchCustomer(getIdFirstCustomer(), customerDto);

        assertNotNull(patchedCustomer);
        assertEquals("Man", patchedCustomer.getLastName());
        assertEquals(originalCustomer.getFirstName(), patchedCustomer.getFirstName());
    }


    private long getIdFirstCustomer() {
        return customerService.findAllCustomers()
                .stream()
                .findFirst()
                .map(CustomerDTO::getId)
                .orElseThrow();
    }
}
