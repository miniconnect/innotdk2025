package hu.webarticum.inno.restdemo.rest;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import hu.webarticum.inno.restdemo.model.PostComment;
import hu.webarticum.inno.restdemo.repository.PostCommentRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@Controller("/post/{postId}/comments")
@Tag(name = "Comments", description = "Endpoints for accessing comments for each post")
class PostCommentController {
    
    private final PostCommentRepository postCommentRepository;
    
    public PostCommentController(PostCommentRepository postCommentRepository) {
        this.postCommentRepository = postCommentRepository;
    }

    @Get("/{id}")
    @Transactional
    Optional<PostCommentDto> findById(@PathVariable Long postId, @PathVariable Long id) {
        
        // TODO: check
        System.out.println(postId);

        return postCommentRepository.findById(id).map(PostCommentDto::from);
    }
    
    @Serdeable
    public static class PostCommentDto {
        
        private final Long id;
        private final String username;
        private final String content;

        public PostCommentDto(String username, String content) {
            this(null, username, content);
        }
        
        @JsonCreator
        public PostCommentDto(
                @JsonProperty(value = "id", required = false) Long id,
                @JsonProperty(value = "username", required = true) String username,
                @JsonProperty(value = "content", required = true) String content) {
            this.id = id;
            this.username = username;
            this.content = content;
        }
        
        public static PostCommentDto from(PostComment comment) {
            return new PostCommentDto(comment.getId(), comment.getUsername(), comment.getContent());
        }

        @JsonInclude(Include.ALWAYS)
        public Long getId() {
            return id;
        }

        @JsonInclude(Include.ALWAYS)
        public String getUsername() {
            return username;
        }

        @JsonInclude(Include.ALWAYS)
        public String getContent() {
            return content;
        }

    }

}
