package us.vicentini.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import us.vicentini.domain.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
