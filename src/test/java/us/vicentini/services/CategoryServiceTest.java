package us.vicentini.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import us.vicentini.api.v1.mapper.CategoryMapper;
import us.vicentini.api.v1.model.CategoryDTO;
import us.vicentini.domain.Category;
import us.vicentini.exceptions.ResourceNotFoundException;
import us.vicentini.repositories.CategoryRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    public static final Long ID = 2L;
    public static final String NAME = "Jimmy";
    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;


    @BeforeEach
    public void setUp() {
        categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
    }


    @Test
    public void getAllCategories() {
        //given
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        //when
        List<CategoryDTO> categoryDTOS = categoryService.findAllCategories();

        //then
        assertEquals(3, categoryDTOS.size());
    }


    @Test
    public void getCategoryByName() {
        //given
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));

        //when
        CategoryDTO categoryDTO = categoryService.findCategoryByName(NAME);

        //then
        assertEquals(ID, categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }


    @Test
    public void shouldNotGetCategoryByName() {
        //given
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());

        //when
        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class, () -> categoryService.findCategoryByName(NAME));

        //then
        assertEquals("Category not found: Jimmy", ex.getMessage());
    }


    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(categoryRepository);
    }
}
