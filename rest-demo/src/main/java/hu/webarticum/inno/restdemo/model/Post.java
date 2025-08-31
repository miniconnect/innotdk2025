package hu.webarticum.inno.restdemo.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hu.webarticum.holodb.jpa.annotation.HoloColumn;
import hu.webarticum.holodb.jpa.annotation.HoloColumnDummyTextKind;
import hu.webarticum.holodb.jpa.annotation.HoloTable;
import hu.webarticum.holodb.jpa.annotation.HoloVirtualColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
@HoloTable(size = 20)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Column(name = "title", nullable = false)
    @HoloColumn(valuesTextKind = HoloColumnDummyTextKind.TITLE)
    private String title;

    @Column(name = "html_content", nullable = false)
    @HoloColumn(valuesTextKind = HoloColumnDummyTextKind.HTML)
    private String htmlContent;

    @Column(name = "tag")
    @ElementCollection
    @CollectionTable(name = "post_tags", joinColumns = { @JoinColumn(name = "post_id") })
    @HoloTable(size = 20)
    @HoloColumn(values = { "educational", "news", "review", "tutorial" })
    @HoloVirtualColumn(name = "color", type = String.class, valuesBundle = "colors")
    private Set<String> tags;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private List<PostComment> comments;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public Set<String> getTags() {
        return new HashSet<>(tags);
    }

    public void setTags(Set<String> tags) {
        this.tags = new HashSet<>(tags);
    }

    public List<PostComment> getComments() {
        return new ArrayList<>(comments);
    }

    public void setComments(List<PostComment> comments) {
        this.comments = new ArrayList<>(comments);
    }


}
