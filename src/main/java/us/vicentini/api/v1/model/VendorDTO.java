package us.vicentini.api.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.vicentini.controllers.v1.VendorController;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class VendorDTO {
    @JsonIgnore
    private Long id;
    @ApiModelProperty(value = "Vendor name", required = true)
    private String name;


    public String getVendorUrl() {
        return VendorController.BASE_PATH + "/" + id;
    }
}
