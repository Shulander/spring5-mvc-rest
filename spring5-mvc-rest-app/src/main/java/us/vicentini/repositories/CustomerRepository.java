package us.vicentini.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import us.vicentini.domain.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
