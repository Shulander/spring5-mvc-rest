package us.vicentini.controllers.v1;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import us.vicentini.api.v1.model.CategoryDTO;
import us.vicentini.api.v1.model.CategoryListDTO;
import us.vicentini.services.CategoryService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@Api(tags = "category-controller",
        produces = APPLICATION_JSON_VALUE + ", " + APPLICATION_XML_VALUE,
        consumes = APPLICATION_JSON_VALUE + ", " + APPLICATION_XML_VALUE)
@RestController
@RequestMapping(CategoryController.BASE_PATH)
@RequiredArgsConstructor
public class CategoryController {
    public static final String BASE_PATH = "/api/v1/categories";

    private final CategoryService categoryService;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public CategoryListDTO listCategories() {
        List<CategoryDTO> categories = categoryService.findAllCategories();
        return new CategoryListDTO(categories);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{name}")
    public CategoryDTO listCategories(@PathVariable String name) {
        return categoryService.findCategoryByName(name);
    }
}
