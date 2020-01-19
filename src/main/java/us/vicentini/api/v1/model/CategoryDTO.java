package us.vicentini.api.v1.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String name;


    public String getCustomerUrl() {
        return CategoryController.BASE_PATH + "/" + getId();
    }
}
