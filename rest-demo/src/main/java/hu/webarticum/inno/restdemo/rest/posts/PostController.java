package hu.webarticum.inno.restdemo.rest.posts;

import java.util.Optional;

import hu.webarticum.inno.restdemo.repository.PostRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import jakarta.transaction.Transactional;

@Controller("/posts")
class PostController {
    
    private final PostRepository postRepository;
    
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Get("/{id}")
    @Transactional
    Optional<PostDto> findById(@PathVariable Long id) {
        return postRepository.findById(id).map(PostDto::from);
    }
    
}
