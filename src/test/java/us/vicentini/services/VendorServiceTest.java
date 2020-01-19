package us.vicentini.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import us.vicentini.api.v1.mapper.VendorMapper;
import us.vicentini.api.v1.model.VendorDTO;
import us.vicentini.domain.Vendor;
import us.vicentini.exceptions.ResourceNotFoundException;
import us.vicentini.repositories.VendorRepository;

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
class VendorServiceTest {
    private static final long ID = 1L;
    private static final String NAME = "John";

    private VendorMapper vendorMapper = VendorMapper.INSTANCE;
    @Mock
    private VendorRepository vendorRepository;
    private VendorService vendorService;


    @BeforeEach
    void setUp() {
        vendorService = new VendorServiceImpl(vendorMapper, vendorRepository);
    }


    @Test
    void getAllVendors() {
        Vendor vendor = createVendor();
        when(vendorRepository.findAll()).thenReturn(Collections.singletonList(vendor));

        List<VendorDTO> vendors = vendorService.findAllVendors();

        assertNotNull(vendors);
        assertEquals(1, vendors.size());
    }


    @Test
    void findVendorById() {
        Vendor vendor = createVendor();
        when(vendorRepository.findById(ID)).thenReturn(Optional.of(vendor));

        VendorDTO vendorDto = vendorService.findVendorsById(ID);

        assertEquals(ID, vendorDto.getId());
        assertEquals(NAME, vendorDto.getName());
    }


    @Test
    void shouldNotFindVendorById() {
        when(vendorRepository.findById(ID)).thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class, () -> vendorService.findVendorsById(ID));

        assertEquals("Vendor not found: 1", ex.getMessage());
    }


    @Test
    void shouldFailWithExceptionVendorNotFound() {
        when(vendorRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> vendorService.findVendorsById(ID));
    }


    @Test
    void shouldSaveNewVendor() {
        VendorDTO vendorDTO = createVendorDTO();
        Vendor vendor = createVendor();
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO persistedVendor = vendorService.createNewVendor(vendorDTO);

        assertNotNull(persistedVendor);
        assertEquals(ID, persistedVendor.getId());
        assertEquals(NAME, persistedVendor.getName());
    }


    @Test
    void shouldUpdateVendor() {
        VendorDTO vendorDTO = createVendorDTO();
        Vendor vendor = createVendor();
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO persistedVendor = vendorService.updateVendor(ID, vendorDTO);

        assertNotNull(persistedVendor);
        assertEquals(ID, persistedVendor.getId());
        assertEquals(NAME, persistedVendor.getName());
    }


    @Test
    void shouldPatchVendor() {
        VendorDTO vendorDTO = spy(createVendorDTO());
        Vendor vendor = spy(Vendor.builder().id(ID).build());
        Vendor patchedVendor = createVendor();
        when(vendorRepository.findById(ID)).thenReturn(Optional.of(vendor));
        when(vendorRepository.save(eq(patchedVendor))).thenReturn(patchedVendor);

        VendorDTO persistedVendor = vendorService.patchVendor(ID, vendorDTO);

        assertNotNull(persistedVendor);
        assertEquals(ID, persistedVendor.getId());
        assertEquals(NAME, persistedVendor.getName());
        verify(vendorDTO).getName();
        verify(vendor).setName(NAME);
    }


    @Test
    void shouldNotPatchVendorNotFound() {
        VendorDTO vendorDTO = spy(createVendorDTO());
        when(vendorRepository.findById(ID)).thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class, () -> vendorService.patchVendor(ID, vendorDTO));

        assertEquals("Vendor not found: 1", ex.getMessage());
    }


    @Test
    void shouldDeleteVendorById() {
        vendorService.deleteVendorById(ID);

        verify(vendorRepository).deleteById(ID);
    }


    private VendorDTO createVendorDTO() {
        return VendorDTO.builder()
                .name(NAME)
                .build();
    }


    private Vendor createVendor() {
        return Vendor.builder()
                .id(ID)
                .name(NAME)
                .build();
    }


    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(vendorRepository);
    }
}
