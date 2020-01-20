package us.vicentini.controllers.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import us.vicentini.api.v1.model.VendorDTO;
import us.vicentini.api.v1.model.VendorListDTO;
import us.vicentini.services.VendorService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = "vendor-controller")
@RestController
@RequestMapping(VendorController.BASE_PATH)
@RequiredArgsConstructor
public class VendorController {
    public static final String BASE_PATH = "/api/v1/vendors";
    private final VendorService vendorService;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List all vendors", produces = APPLICATION_JSON_VALUE)
    public VendorListDTO findAllVendors() {
        List<VendorDTO> vendors = vendorService.findAllVendors();
        return new VendorListDTO(vendors);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find vendor by Id", produces = APPLICATION_JSON_VALUE)
    public VendorDTO findVendorById(@PathVariable Long id) {
        return vendorService.findVendorsById(id);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create a new vendor", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
        return vendorService.createNewVendor(vendorDTO);
    }


    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update a vendor identified by id", produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    public VendorDTO updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.updateVendor(id, vendorDTO);
    }


    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Partial update a vendor identified by id", produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    public VendorDTO patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.patchVendor(id, vendorDTO);
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete a vendor identified by id")
    public void deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendorById(id);
    }
}
