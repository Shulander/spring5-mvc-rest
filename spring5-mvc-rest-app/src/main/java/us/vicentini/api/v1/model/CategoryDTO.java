package us.vicentini.api.v1.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.vicentini.controllers.v1.CategoryController;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryDTO {
    @JsonIgnore
    private Long id;
    @ApiModelProperty(value = "Category name", required = true)
    private String name;


    public String getCategoryUrl() {
        return CategoryController.BASE_PATH + "/" + getId();
    }
}
