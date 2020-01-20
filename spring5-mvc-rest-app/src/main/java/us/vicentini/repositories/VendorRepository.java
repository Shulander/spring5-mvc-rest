package us.vicentini.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import us.vicentini.domain.Vendor;


public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
