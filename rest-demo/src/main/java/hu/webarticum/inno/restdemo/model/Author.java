package hu.webarticum.inno.restdemo.model;

import hu.webarticum.holodb.jpa.annotation.HoloColumn;
import hu.webarticum.holodb.jpa.annotation.HoloTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "authors")
@HoloTable(size = 20)
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", nullable = false)
    @HoloColumn(valuesBundle = "forenames")
    private String firstname;

    @Column(name = "lastname", nullable = false)
    @HoloColumn(valuesBundle = "surnames")
    private String lastname;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}
