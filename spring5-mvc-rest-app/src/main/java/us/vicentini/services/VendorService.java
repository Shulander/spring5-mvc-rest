package us.vicentini.services;

import us.vicentini.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {
    List<VendorDTO> findAllVendors();

    VendorDTO findVendorsById(Long id);

    VendorDTO createNewVendor(VendorDTO vendorDTO);

    VendorDTO updateVendor(Long id, VendorDTO vendorDTO);

    VendorDTO patchVendor(Long id, VendorDTO vendorDTO);

    void deleteVendorById(Long id);
}
