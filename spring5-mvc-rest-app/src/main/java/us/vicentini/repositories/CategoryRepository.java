package us.vicentini.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import us.vicentini.domain.Category;

import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
