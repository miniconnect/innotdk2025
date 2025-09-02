package hu.webarticum.inno.restdemo.rest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import hu.webarticum.inno.restdemo.model.PostComment;
import hu.webarticum.inno.restdemo.repository.PostCommentRepository;
import hu.webarticum.inno.restdemo.repository.PostRepository;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@Controller("/post/{postId}/comments")
@Tag(name = "Comments", description = "Endpoints for accessing comments for each post")
class PostCommentController {
    
    private final PostRepository postRepository;
    
    private final PostCommentRepository postCommentRepository;
    
    public PostCommentController(PostRepository postRepository, PostCommentRepository postCommentRepository) {
        this.postRepository = postRepository;
        this.postCommentRepository = postCommentRepository;
    }

    @Get("/")
    @Transactional
    public List<PostCommentDto> list(@PathVariable long postId) {
        checkPostId(postId);
        return postCommentRepository.findByPostIdOrderByCreatedAt(postId).stream().map(PostCommentDto::from).toList();
    }

    @Get("/{id}")
    @Transactional
    Optional<PostCommentDto> get(@PathVariable long postId, @PathVariable Long id) {
        checkPostId(postId);
        return postCommentRepository.findById(id).map(PostCommentDto::from);
    }

    private void checkPostId(long postId) {
        if (!postRepository.existsById(postId)) {
            throw new HttpStatusException(HttpStatus.NOT_FOUND, "No such post");
        }
    }
    
    @Serdeable
    public static class PostCommentDto {
        
        private final Long id;
        private final LocalDateTime createdAt;
        private final String username;
        private final String content;

        @JsonCreator
        public PostCommentDto(
                @JsonProperty(value = "id", required = false) Long id,
                @JsonProperty(value = "createdAt", required = true) LocalDateTime createdAt,
                @JsonProperty(value = "username", required = true) String username,
                @JsonProperty(value = "content", required = true) String content) {
            this.id = id;
            this.createdAt = createdAt;
            this.username = username;
            this.content = content;
        }
        
        public static PostCommentDto from(PostComment comment) {
            return new PostCommentDto(comment.getId(), comment.getCreatedAt(), comment.getUsername(), comment.getContent());
        }

        @JsonInclude(Include.ALWAYS)
        public Long getId() {
            return id;
        }

        @JsonInclude(Include.ALWAYS)
        public LocalDateTime getCreatedAt() {
            return createdAt;
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
