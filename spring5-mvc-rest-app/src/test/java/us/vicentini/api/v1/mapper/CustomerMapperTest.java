package us.vicentini.api.v1.mapper;

import org.junit.jupiter.api.Test;
import us.vicentini.api.v1.model.CustomerDTO;
import us.vicentini.controllers.v1.CustomerController;
import us.vicentini.domain.Customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerMapperTest {
    private static final long ID = 1L;
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";

    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;


    @Test
    public void categoryToCategoryDTO() {
        //given
        Customer customer = Customer.builder()
                .id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME).build();

        //when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        //then
        assertEquals(Long.valueOf(ID), customerDTO.getId());
        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertEquals(LAST_NAME, customerDTO.getLastName());
        assertEquals(CustomerController.BASE_PATH + "/" + ID, customerDTO.getCustomerUrl());
    }


    @Test
    public void customerDTOToCustomer() {
        //given
        CustomerDTO customerDTO = CustomerDTO.builder()
                .id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME).build();
        //when
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);

        //then
        assertEquals(Long.valueOf(ID), customer.getId());
        assertEquals(FIRST_NAME, customer.getFirstName());
        assertEquals(LAST_NAME, customer.getLastName());
    }
}
