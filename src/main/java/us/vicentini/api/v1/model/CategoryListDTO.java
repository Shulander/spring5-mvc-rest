package us.vicentini.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryListDTO {
    @ApiModelProperty(value = "List of categories")
    private final List<CategoryDTO> categories;
}
