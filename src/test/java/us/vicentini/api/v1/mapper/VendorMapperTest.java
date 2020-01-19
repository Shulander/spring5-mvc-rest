package us.vicentini.api.v1.mapper;

import org.junit.jupiter.api.Test;
import us.vicentini.api.v1.model.VendorDTO;
import us.vicentini.controllers.v1.VendorController;
import us.vicentini.domain.Vendor;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VendorMapperTest {
    public static final String NAME = "Joe";
    public static final long ID = 1L;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;


    @Test
    public void vendorToVendorDTO() {
        //given
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);

        //when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        //then
        assertEquals(Long.valueOf(ID), vendorDTO.getId());
        assertEquals(NAME, vendorDTO.getName());
        assertEquals(VendorController.BASE_PATH + "/" + ID, vendorDTO.getVendorUrl());
    }


    @Test
    public void vendorDTOToVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);
        vendorDTO.setId(ID);

        //when
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

        //then
        assertEquals(Long.valueOf(ID), vendor.getId());
        assertEquals(NAME, vendor.getName());
    }
}
