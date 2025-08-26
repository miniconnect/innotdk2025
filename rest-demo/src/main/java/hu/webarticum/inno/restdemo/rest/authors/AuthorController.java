package hu.webarticum.inno.restdemo.rest.authors;

import java.util.Optional;

import hu.webarticum.inno.restdemo.repository.AuthorRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import jakarta.transaction.Transactional;

@Controller("/authors")
class PostController {
    
    private final AuthorRepository authorRepository;
    
    public PostController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Get("/{id}")
    @Transactional
    Optional<AuthorDto> findById(@PathVariable Long id) {
        return authorRepository.findById(id).map(AuthorDto::from);
    }
    
}
