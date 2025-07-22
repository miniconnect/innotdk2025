package hu.webarticum.inno.restdemo.rest;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import hu.webarticum.inno.restdemo.model.Post;

public class PostDto {
    
    private final long id;
    private final String title;
    private final String description;
    private final Set<String> tags;

    private PostDto(long id, String title, String description, Set<String> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.tags = Collections.unmodifiableSet(new TreeSet<>(tags));
    }
    
    public static PostDto from(Post post) {
        return new PostDto(post.getId(), post.getTitle(), post.getDescription(), post.getTags());
    }

    @JsonInclude(Include.ALWAYS)
    public long getId() {
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
