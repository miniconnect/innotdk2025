package hu.webarticum.inno.restdemo.rest;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import hu.webarticum.inno.restdemo.model.Post;
import hu.webarticum.inno.restdemo.repository.PostRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@Controller("/posts")
@Tag(name = "Post", description = "Endpoints for accessing posts")
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
    
    @Serdeable
    public static class PostDto {
        
        private final Long id;
        private final String title;
        private final String description;
        private final Set<String> tags;

        public PostDto(String title, String description, Set<String> tags) {
            this(null, title, description, tags);
        }

        @JsonCreator
        public PostDto(
                @JsonProperty(value = "id", required = false) Long id,
                @JsonProperty(value = "title", required = true) String title,
                @JsonProperty(value = "description", required = true) String description,
                @JsonProperty(value = "tags", required = false) Set<String> tags) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.tags =
                    (tags != null && !tags.isEmpty()) ?
                    Collections.unmodifiableSet(new TreeSet<>(tags)) :
                    Collections.emptySet();
        }
        
        public static PostDto from(Post post) {
            return new PostDto(post.getId(), post.getTitle(), post.getDescription(), post.getTags());
        }

        @JsonInclude(Include.ALWAYS)
        public Long getId() {
            return id;
        }

        @JsonInclude(Include.ALWAYS)
        public String getTitle() {
            return title;
        }

        @JsonInclude(Include.ALWAYS)
        public String getDescription() {
            return description;
        }
        
        @JsonInclude(Include.ALWAYS)
        public Set<String> getTags() {
            return tags;
        }

    }

}
