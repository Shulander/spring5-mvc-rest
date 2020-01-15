package us.vicentini.controllers.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import us.vicentini.api.v1.model.CategoryDTO;
import us.vicentini.api.v1.model.CategoryListDTO;
import us.vicentini.services.CategoryService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @ResponseBody
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryListDTO> listCategories() {
        List<CategoryDTO> categories = categoryService.findAllCategories();
        return ResponseEntity.ok(new CategoryListDTO(categories));
    }


    @ResponseBody
    @GetMapping(value = "/{name}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDTO> listCategories(@PathVariable String name) {
        CategoryDTO category = categoryService.findCategoryByName(name);
        return ResponseEntity.ok(category);
    }
}
