package us.vicentini.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import us.vicentini.api.v1.mapper.VendorMapper;
import us.vicentini.api.v1.model.VendorDTO;
import us.vicentini.domain.Vendor;
import us.vicentini.exceptions.ResourceNotFoundException;
import us.vicentini.repositories.VendorRepository;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {
    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;


    @Override
    public List<VendorDTO> findAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendorMapper::vendorToVendorDTO)
                .collect(Collectors.toList());
    }


    @Override
    public VendorDTO findVendorsById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::vendorToVendorDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found: " + id));
    }


    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        Vendor savedVendor = vendorRepository.save(vendor);
        return vendorMapper.vendorToVendorDTO(savedVendor);
    }


    @Override
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendor.setId(id);
        Vendor savedVendor = vendorRepository.save(vendor);
        return vendorMapper.vendorToVendorDTO(savedVendor);
    }


    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    copyIfNonNull(vendorDTO::getName, vendor::setName);
                    return vendor;
                })
                .map(vendorRepository::save)
                .map(vendorMapper::vendorToVendorDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found: " + id));
    }


    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }


    private <T> void copyIfNonNull(Supplier<T> s, Consumer<T> c) {
        T value = s.get();
        if (value != null) {
            c.accept(value);
        }
    }
}
