package com.football.assistant.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "player")
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private Integer apiId;

    @Column
    private java.sql.Timestamp lastRefreshTimestamp;

    @Column(length = 512)
    private String nationality;

    @Column
    private String fullName;

    @Column
    private String dateBorn;

    @Column
    private String wage;

    @Column
    private String birthLocation;

    @Column(length = 10000)
    private String description;

    @Column
    private String position;

    @Column
    private double height;

    @Column
    private double weight;

    @Column
    private String thumbnailUrl;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private FootballClub club;

    public Player(Integer apiId, Timestamp lastRefreshTimestamp, String nationality, String fullName, String dateBorn,
                  String wage, String birthLocation, String description, String position, double height, double weight,
                  String thumbnailUrl) {
        this.apiId = apiId;
        this.lastRefreshTimestamp = lastRefreshTimestamp;
        this.nationality = nationality;
        this.fullName = fullName;
        this.dateBorn = dateBorn;
        this.wage = wage;
        this.birthLocation = birthLocation;
        this.description = description;
        this.position = position;
        this.height = height;
        this.weight = weight;
        this.thumbnailUrl = thumbnailUrl;
        this.club = null;
    }

    protected Player() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getApiId() {
        return apiId;
    }

    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    public Timestamp getLastRefreshTimestamp() {
        return lastRefreshTimestamp;
    }

    public void setLastRefreshTimestamp(Timestamp lastRefreshTimestamp) {
        this.lastRefreshTimestamp = lastRefreshTimestamp;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateBorn() {
        return dateBorn;
    }

    public void setDateBorn(String dateBorn) {
        this.dateBorn = dateBorn;
    }

    public String getWage() {
        return wage;
    }

    public void setWage(String wage) {
        this.wage = wage;
    }

    public String getBirthLocation() {
        return birthLocation;
    }

    public void setBirthLocation(String birthLocation) {
        this.birthLocation = birthLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public FootballClub getClub() {
        return club;
    }

    public void setClub(FootballClub club) {
        this.club = club;
    }
}
