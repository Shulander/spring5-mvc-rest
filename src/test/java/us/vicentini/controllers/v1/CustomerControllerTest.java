package us.vicentini.controllers.v1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import us.vicentini.api.v1.model.CustomerDTO;
import us.vicentini.controllers.RestControllerAdvice;
import us.vicentini.exceptions.ResourceNotFoundException;
import us.vicentini.services.CustomerService;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static us.vicentini.util.ObjectUtil.asJsonString;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    CustomerService customerService;
    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;


    @BeforeEach
    public void setUp() {
        RestControllerAdvice controllerAdvice = new RestControllerAdvice();
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(controllerAdvice).build();
    }


    @Test
    void shouldListAllCustomers() throws Exception {
        CustomerDTO customer1 = CustomerDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();
        CustomerDTO customer2 = CustomerDTO.builder()
                .id(2L)
                .firstName("Lemuel")
                .lastName("Gulliver")
                .build();
        when(customerService.findAllCustomers()).thenReturn(List.of(customer1, customer2));

        mockMvc.perform(get(CustomerController.BASE_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }


    @Test
    void shouldFindCustomerById() throws Exception {
        CustomerDTO customer = CustomerDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();
        when(customerService.findCustomersById(1L)).thenReturn(customer);

        mockMvc.perform(get(CustomerController.BASE_PATH + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("John")))
                .andExpect(jsonPath("$.lastName", equalTo("Doe")))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_PATH + "/1")));

    }


    @Test
    void shouldCreateNewCustomer() throws Exception {
        CustomerDTO customer = CustomerDTO.builder()
                .firstName("Bruce")
                .lastName("Wayne").build();
        CustomerDTO persistedCustomer = customer.toBuilder()
                .id(1L).build();
        when(customerService.createNewCustomer(eq(customer))).thenReturn(persistedCustomer);

        mockMvc.perform(post(CustomerController.BASE_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo("Bruce")))
                .andExpect(jsonPath("$.lastName", equalTo("Wayne")))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_PATH + "/1")));
    }


    @Test
    void shouldUpdateCustomer() throws Exception {
        CustomerDTO customer = CustomerDTO.builder()
                .firstName("Bruce")
                .lastName("Wayne").build();
        CustomerDTO persistedCustomer = customer.toBuilder()
                .id(1L).build();
        when(customerService.updateCustomer(eq(1L), eq(customer))).thenReturn(persistedCustomer);

        mockMvc.perform(put(CustomerController.BASE_PATH + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Bruce")))
                .andExpect(jsonPath("$.lastName", equalTo("Wayne")))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_PATH + "/1")));
    }


    @Test
    void shouldPatchCustomer() throws Exception {
        CustomerDTO customer = CustomerDTO.builder()
                .firstName("Bruce")
                .lastName("Wayne").build();
        CustomerDTO persistedCustomer = customer.toBuilder()
                .id(1L).build();
        when(customerService.patchCustomer(eq(1L), eq(customer))).thenReturn(persistedCustomer);

        mockMvc.perform(patch(CustomerController.BASE_PATH + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Bruce")))
                .andExpect(jsonPath("$.lastName", equalTo("Wayne")))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_PATH + "/1")));
    }


    @Test
    void shouldDeleteCustomer() throws Exception {
        mockMvc.perform(delete(CustomerController.BASE_PATH + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(1L);
    }


    @Test
    void shouldReturnNotFound() throws Exception {
        ResourceNotFoundException notFoundException = new ResourceNotFoundException("Resource Not Found");
        when(customerService.findCustomersById(1L)).thenThrow(notFoundException);

        mockMvc.perform(get(CustomerController.BASE_PATH + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", equalTo("Resource Not Found")));
    }


    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(customerService);
    }
}
