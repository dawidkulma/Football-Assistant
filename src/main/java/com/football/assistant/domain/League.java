package com.football.assistant.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "league")
public class League implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "league_id")
    private int id;

    @Column
    private Integer apiId;

    @Column
    private java.sql.Timestamp lastRefreshTimestamp;

    @Column(length = 512)
    private String fullName;

    @Column
    private Integer formedYear;

    @Column(length = 512)
    private String websiteUrl;

    @Column
    private String description;

    @Column
    private String logoUrl;

    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL)
    private Set<FootballClub> clubs;

    public League(Integer apiId, Timestamp lastRefreshTimestamp, String fullName, Integer formedYear, String websiteUrl,
                  String description, String logoUrl) {
        this.apiId = apiId;
        this.lastRefreshTimestamp = lastRefreshTimestamp;
        this.fullName = fullName;
        this.formedYear = formedYear;
        this.websiteUrl = websiteUrl;
        this.description = description;
        this.logoUrl = logoUrl;
        this.clubs = new HashSet<>();
    }

    protected League() {}

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getFormedYear() {
        return formedYear;
    }

    public void setFormedYear(Integer formedYear) {
        this.formedYear = formedYear;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Set<FootballClub> getClubs() {
        return clubs;
    }

    public void setClubs(Set<FootballClub> clubs) {
        this.clubs = clubs;
    }

    public void addClub(FootballClub club) {
        if(!this.clubs.contains(club)) {
            this.clubs.add(club);
            club.setLeague(this);
        }
    }

    public void removeClub(FootballClub club) {
        if(this.clubs.contains(club)) {
            this.clubs.remove(club);
        }
    }
}
