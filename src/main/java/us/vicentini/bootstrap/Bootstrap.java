package us.vicentini.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import us.vicentini.domain.Category;
import us.vicentini.domain.Customer;
import us.vicentini.repositories.CategoryRepository;
import us.vicentini.repositories.CustomerRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;


    @Override
    public void run(String... args) {
        loadCategories();
        loadCustomers();
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
