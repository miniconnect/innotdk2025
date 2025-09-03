package hu.webarticum.inno.restdemo.rest;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import hu.webarticum.inno.restdemo.model.Category;
import hu.webarticum.inno.restdemo.repository.CategoryRepository;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@Controller("/categories")
@Tag(name = "Categories", description = "Endpoints for accessing post categories")
public class CategoryController {
    
    private final CategoryRepository categoryRepository;
    
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Get("/")
    @Transactional
    public List<CategoryDto> list() {
        return categoryRepository.findAll().stream().map(CategoryDto::from).toList();
    }

    @Get("/{id}")
    @Transactional
    public Optional<CategoryDto> get(@PathVariable Long id) {
        return categoryRepository.findById(id).map(CategoryDto::from);
    }

    @Post("/")
    @Transactional
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = categoryDto.toCategory();
        Category savedCategory = categoryRepository.save(category);
        return CategoryDto.from(savedCategory);
    }

    @Put("/{id}")
    @Transactional
    public CategoryDto update(@PathVariable Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "No such category"));
        Long givenId = categoryDto.getId();
        if (givenId != null && givenId != id) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Changing 'id' is not allowed");
        }
        categoryDto.mergeToCategory(category);
        Category savedCategory = categoryRepository.update(category);
        return CategoryDto.from(savedCategory);
    }

    @Serdeable
    public static class CategoryDto {
        
        private final Long id;
        private final String name;
        private final String description;

        @JsonCreator
        public CategoryDto(
                @JsonProperty(value = "id", required = false) Long id,
                @JsonProperty(value = "name", required = false) String name,
                @JsonProperty(value = "description", required = false) String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }
        
        public static CategoryDto from(Category category) {
            return new CategoryDto(category.getId(), category.getName(), category.getDescription());
        }

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        @JsonInclude(Include.NON_NULL)
        public Long getId() {
            return id;
        }

        @JsonInclude(Include.NON_NULL)
        public String getName() {
            return name;
        }

        @JsonInclude(Include.NON_NULL)
        public String getDescription() {
            return description;
        }

        public Category toCategory() {
            Category category = new Category();
            mergeToCategory(category);
            return category;
        }

        public void mergeToCategory(Category category) {
            if (id != null) {
                category.setId(id);
            }
            if (name != null) {
                category.setName(name);
            }
            if (description != null) {
                category.setDescription(description);
            }
        }

    }
    
}
