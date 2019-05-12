package com.football.assistant.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "football_club")
public class FootballClub implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "club_id")
    private Integer id;

    @Column(length = 512)
    private String fullName;

    @Column
    private Integer apiId;

    @Column
    private Integer formedYear;

    @Column(length = 512)
    private String stadium;

    @Column(length = 512)
    private String websiteUrl;

    @Column(length =  10000)
    private String description;

    @Column
    private String logoUrl;

    @Column
    private java.sql.Timestamp lastRefreshTimestamp;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private Set<Player> players;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

    public FootballClub(String fullName, Integer apiId, Integer formedYear, String stadium,
                        String websiteUrl, String description, String logoUrl, Timestamp lastRefreshTimestamp) {
        this.fullName = fullName;
        this.apiId = apiId;
        this.formedYear = formedYear;
        this.stadium = stadium;
        this.websiteUrl = websiteUrl;
        this.description = description;
        this.logoUrl = logoUrl;
        this.lastRefreshTimestamp = lastRefreshTimestamp;
        this.players = new HashSet<>();
        this.league = null;
    }

    protected FootballClub() {
        this.players = new HashSet<>();
    }

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

    public Integer getApiId() {
        return apiId;
    }

    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    public Integer getFormedYear() {
        return formedYear;
    }

    public void setFormedYear(Integer formedYear) {
        this.formedYear = formedYear;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
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

    public Timestamp getLastRefreshTimestamp() {
        return lastRefreshTimestamp;
    }

    public void setLastRefreshTimestamp(Timestamp lastRefreshTimestamp) {
        this.lastRefreshTimestamp = lastRefreshTimestamp;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public void addPlayer(Player player) {
        if(!this.players.contains(player)) {
            this.players.add(player);
            player.setClub(this);
        }
    }

    public void removePlayer(Player player) {
        if(this.players.contains(player)) {
            this.players.remove(player);
        }
    }

}
