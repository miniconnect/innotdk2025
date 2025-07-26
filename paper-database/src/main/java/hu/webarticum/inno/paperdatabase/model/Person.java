package hu.webarticum.inno.paperdatabase.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import hu.webarticum.holodb.jpa.annotation.HoloColumn;
import hu.webarticum.holodb.jpa.annotation.HoloColumnShuffleQuality;
import hu.webarticum.holodb.jpa.annotation.HoloTable;

@Entity
@Table(name = "persons")
@HoloTable(size = 2000)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @HoloColumn(valuesBundle = "forenames", shuffleQuality = HoloColumnShuffleQuality.HIGH)
    private String firstname;

    @Column(nullable = false)
    @HoloColumn(valuesBundle = "surnames", shuffleQuality = HoloColumnShuffleQuality.MEDIUM)
    private String lastname;

    @Column(nullable = false, name="birth_year")
    //private Year birthYear; // FIXME
    @HoloColumn(valuesRange = { 1960, 2005 })
    private Integer birthYear;

    
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

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

}
