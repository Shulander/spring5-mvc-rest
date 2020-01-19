package us.vicentini.api.v1.mapper;

import org.junit.jupiter.api.Test;
import us.vicentini.api.v1.model.CategoryDTO;
import us.vicentini.controllers.v1.CategoryController;
import us.vicentini.domain.Category;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryMapperTest {
    public static final String NAME = "Joe";
    public static final long ID = 1L;

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;


    @Test
    public void categoryToCategoryDTO() {

        //given
        Category category = new Category();
        category.setName(NAME);
        category.setId(ID);

        //when
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        //then
        assertEquals(Long.valueOf(ID), categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
        assertEquals(CategoryController.BASE_PATH + "/" + ID, categoryDTO.getCategoryUrl());
    }
}
