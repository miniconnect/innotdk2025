package hu.webarticum.inno.restdemo.rest;

import java.util.Optional;

import javax.transaction.Transactional;

import hu.webarticum.inno.restdemo.repository.PostRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;

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
