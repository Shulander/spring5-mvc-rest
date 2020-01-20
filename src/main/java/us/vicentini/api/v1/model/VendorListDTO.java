package us.vicentini.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class VendorListDTO {
    @ApiModelProperty(value = "List of vendors")
    private final List<VendorDTO> vendors;
}
