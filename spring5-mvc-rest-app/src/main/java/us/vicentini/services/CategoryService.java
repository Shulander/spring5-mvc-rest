package us.vicentini.services;

import us.vicentini.api.v1.model.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> findAllCategories();

    CategoryDTO findCategoryByName(String name);
}
