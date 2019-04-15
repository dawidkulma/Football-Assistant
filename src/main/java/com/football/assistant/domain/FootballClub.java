package com.football.assistant.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "football_club")
public class FootballClub implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 512)
    private String fullName;

    @Column(length = 512)
    private String shortName;

    public FootballClub(String fullName, String shortName) {
        this.fullName = fullName;
        this.shortName = shortName;
    }

    protected FootballClub() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return "FootballClub{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", shortName='" + shortName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FootballClub that = (FootballClub) o;
        return id.equals(that.id) &&
                Objects.equals(fullName, that.fullName) &&
                Objects.equals(shortName, that.shortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, shortName);
    }
}
