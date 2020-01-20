package us.vicentini.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import us.vicentini.domain.Category;
import us.vicentini.domain.Customer;
import us.vicentini.domain.Vendor;
import us.vicentini.repositories.CategoryRepository;
import us.vicentini.repositories.CustomerRepository;
import us.vicentini.repositories.VendorRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;


    @Override
    public void run(String... args) {
        loadCategories();
        loadCustomers();
        loadVendors();
    }


    private void loadVendors() {
        Vendor vendor1 = Vendor.builder().name("Franks Fresh Fruits from France Ltd.").build();
        Vendor vendor2 = Vendor.builder().name("Traina Dried Fruit").build();
        vendorRepository.save(vendor1);
        vendorRepository.save(vendor2);
        log.info("Vendor Data Loaded: {}", vendorRepository.count());
    }


    private void loadCustomers() {
        Customer customer1 = Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .build();
        Customer customer2 = Customer.builder()
                .firstName("Lemuel")
                .lastName("Gulliver")
                .build();

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        log.info("Customer Data Loaded: {}", customerRepository.count());
    }


    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        log.info("Category Data Loaded: {}", categoryRepository.count());
    }
}
