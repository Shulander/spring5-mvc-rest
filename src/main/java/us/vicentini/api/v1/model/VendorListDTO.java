package us.vicentini.api.v1.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class VendorListDTO {
    private final List<VendorDTO> vendors;
}
