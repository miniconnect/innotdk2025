package hu.webarticum.inno.restdemo.rest.authors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import hu.webarticum.inno.restdemo.model.Author;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class AuthorDto {
    
    private final long id;
    private final String firstname;
    private final String lastname;

    private AuthorDto(long id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }
    
    public static AuthorDto from(Author author) {
        return new AuthorDto(author.getId(), author.getFirstname(), author.getLastname());
    }

    @JsonInclude(Include.ALWAYS)
    public long getId() {
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
