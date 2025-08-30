package hu.webarticum.inno.restdemo.rest;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import hu.webarticum.inno.restdemo.model.Category;
import hu.webarticum.inno.restdemo.repository.CategoryRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@Controller("/categories")
@Tag(name = "Categories", description = "Endpoints for accessing post categories")
class CategoryController {
    
    private final CategoryRepository categoryRepository;
    
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Get("/{id}")
    @Transactional
    Optional<CategoryDto> findById(@PathVariable Long id) {
        return categoryRepository.findById(id).map(CategoryDto::from);
    }

    @Serdeable
    public static class CategoryDto {
        
        private final long id;
        private final String name;
        private final String description;

        public CategoryDto(String name, String description) {
            this(null, name, description);
        }

        @JsonCreator
        public CategoryDto(
                @JsonProperty(value = "id", required = false) Long id,
                @JsonProperty(value = "name", required = true) String name,
                @JsonProperty(value = "description", required = true) String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }
        
        public static CategoryDto from(Category category) {
            return new CategoryDto(category.getId(), category.getName(), category.getDescription());
        }

        @JsonInclude(Include.ALWAYS)
        public Long getId() {
            return id;
        }

        @JsonInclude(Include.ALWAYS)
        public String getName() {
            return name;
        }

        @JsonInclude(Include.ALWAYS)
        public String getDescription() {
            return description;
        }

    }
    
}
