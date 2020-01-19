package us.vicentini.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import us.vicentini.api.v1.model.VendorDTO;
import us.vicentini.domain.Vendor;

@Mapper
public interface VendorMapper {
    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    VendorDTO vendorToVendorDTO(Vendor vendor);

    Vendor vendorDTOToVendor(VendorDTO vendorDTO);
}
