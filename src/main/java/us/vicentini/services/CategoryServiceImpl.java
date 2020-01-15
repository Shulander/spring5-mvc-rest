package us.vicentini.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import us.vicentini.api.v1.mapper.CategoryMapper;
import us.vicentini.api.v1.model.CategoryDTO;
import us.vicentini.domain.Category;
import us.vicentini.repositories.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;


    @Override
    public List<CategoryDTO> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
    }


    @Override
    public CategoryDTO findCategoryByName(String name) {
        Category category = categoryRepository.findByName(name);
        return categoryMapper.categoryToCategoryDTO(category);
    }
}
