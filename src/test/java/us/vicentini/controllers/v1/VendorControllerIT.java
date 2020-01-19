package us.vicentini.controllers.v1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import us.vicentini.api.v1.model.VendorDTO;
import us.vicentini.exceptions.ResourceNotFoundException;
import us.vicentini.services.VendorService;

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

@ExtendWith(SpringExtension.class)
@WebMvcTest(VendorController.class)
class VendorControllerIT {

    @MockBean
    VendorService vendorService;

    @Autowired
    MockMvc mockMvc;


    @Test
    void shouldListAllVendors() throws Exception {
        VendorDTO vendor1 = VendorDTO.builder()
                .id(1L)
                .name("Franks Fresh Fruits from France Ltd.")
                .build();
        VendorDTO vendor2 = VendorDTO.builder()
                .id(2L)
                .name("Lemuel")
                .build();
        when(vendorService.findAllVendors()).thenReturn(List.of(vendor1, vendor2));

        mockMvc.perform(get(VendorController.BASE_PATH)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));

        verify(vendorService).findAllVendors();
    }


    @Test
    void shouldFindVendorById() throws Exception {
        VendorDTO vendor = VendorDTO.builder()
                .id(1L)
                .name("Franks Fresh Fruits from France Ltd.")
                .build();
        when(vendorService.findVendorsById(1L)).thenReturn(vendor);

        mockMvc.perform(get(VendorController.BASE_PATH + "/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Franks Fresh Fruits from France Ltd.")))
                .andExpect(jsonPath("$.vendorUrl", equalTo(VendorController.BASE_PATH + "/1")));

        verify(vendorService).findVendorsById(1L);
    }


    @Test
    void shouldCreateNewVendor() throws Exception {
        VendorDTO vendor = VendorDTO.builder()
                .name("Franks Fresh Fruits from France Ltd.").build();
        VendorDTO persistedVendor = vendor.toBuilder()
                .id(1L).build();
        when(vendorService.createNewVendor(eq(vendor))).thenReturn(persistedVendor);

        mockMvc.perform(post(VendorController.BASE_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(vendor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Franks Fresh Fruits from France Ltd.")))
                .andExpect(jsonPath("$.vendorUrl", equalTo(VendorController.BASE_PATH + "/1")));

        verify(vendorService).createNewVendor(eq(vendor));
    }


    @Test
    void shouldUpdateVendor() throws Exception {
        VendorDTO vendor = VendorDTO.builder()
                .name("Franks Fresh Fruits from France Ltd.").build();
        VendorDTO persistedVendor = vendor.toBuilder()
                .id(1L).build();
        when(vendorService.updateVendor(eq(1L), eq(vendor))).thenReturn(persistedVendor);

        mockMvc.perform(put(VendorController.BASE_PATH + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Franks Fresh Fruits from France Ltd.")))
                .andExpect(jsonPath("$.vendorUrl", equalTo(VendorController.BASE_PATH + "/1")));

        verify(vendorService).updateVendor(eq(1L), eq(vendor));
    }


    @Test
    void shouldPatchVendor() throws Exception {
        VendorDTO vendor = VendorDTO.builder().name("Franks Fresh Fruits from France Ltd.").build();
        VendorDTO persistedVendor = vendor.toBuilder()
                .id(1L).build();
        when(vendorService.patchVendor(eq(1L), eq(vendor))).thenReturn(persistedVendor);

        mockMvc.perform(patch(VendorController.BASE_PATH + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Franks Fresh Fruits from France Ltd.")))
                .andExpect(jsonPath("$.vendorUrl", equalTo(VendorController.BASE_PATH + "/1")));

        verify(vendorService).patchVendor(eq(1L), eq(vendor));
    }


    @Test
    void shouldDeleteVendor() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_PATH + "/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService).deleteVendorById(1L);
    }


    @Test
    void shouldReturnNotFound() throws Exception {
        ResourceNotFoundException notFoundException = new ResourceNotFoundException("Resource Not Found");
        when(vendorService.findVendorsById(1L)).thenThrow(notFoundException);

        mockMvc.perform(get(VendorController.BASE_PATH + "/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", equalTo("Resource Not Found")));

        verify(vendorService).findVendorsById(1L);
    }


    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(vendorService);
    }
}
