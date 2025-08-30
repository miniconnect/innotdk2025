package hu.webarticum.inno.restdemo.rest;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import hu.webarticum.inno.restdemo.model.Author;
import hu.webarticum.inno.restdemo.repository.AuthorRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@Controller("/authors")
@Tag(name = "Authors", description = "Endpoints for accessing post authors")
class AuthorController {
    
    private final AuthorRepository authorRepository;
    
    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Get("/{id}")
    @Transactional
    Optional<AuthorDto> findById(@PathVariable Long id) {
        return authorRepository.findById(id).map(AuthorDto::from);
    }

    @Serdeable
    public static class AuthorDto {
        
        private final Long id;
        private final String firstname;
        private final String lastname;

        public AuthorDto(String firstname, String lastname) {
            this(null, firstname, lastname);
        }

        @JsonCreator
        public AuthorDto(
                @JsonProperty(value = "id", required = false) Long id,
                @JsonProperty(value = "firstname", required = true) String firstname,
                @JsonProperty(value = "lastname", required = true) String lastname) {
            this.id = id;
            this.firstname = firstname;
            this.lastname = lastname;
        }
        
        public static AuthorDto from(Author author) {
            return new AuthorDto(author.getId(), author.getFirstname(), author.getLastname());
        }

        @JsonInclude(Include.ALWAYS)
        public Long getId() {
            return id;
        }

        @JsonInclude(Include.ALWAYS)
        public String getFirstname() {
            return firstname;
        }

        @JsonInclude(Include.ALWAYS)
        public String getLastname() {
            return lastname;
        }

    }
    
}
