package com.football.assistant.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "\"users\"")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    @Column(name = "email", length = 512)
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Column(name = "nickname", length = 512)
    @NotEmpty(message = "*Please provide your nickname")
    private String nickname;

    @Column(name = "active")
    private int active;

    @Column(name = "oauth2id")
    private String oauth2id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_club", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "club_id"))
    private Set<FootballClub> followedClubs;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_league", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "league_id"))
    private Set<League> followedLeagues;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<NewsPost> newsPosts;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<PostComment> postsComments;

    public User(@Email(message = "*Please provide a valid Email") @NotEmpty(message = "*Please provide an email") String email,
                @Length(min = 5, message = "*Your password must have at least 5 characters")
                @NotEmpty(message = "*Please provide your password") String password,
                @NotEmpty(message = "*Please provide your nickname") String nickname, int active, String oauth2id) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.active = active;
        this.oauth2id = oauth2id;
        this.roles = new HashSet<>();
        this.newsPosts = new HashSet<>();
        this.postsComments = new HashSet<>();
        this.followedClubs = new HashSet<>();
        this.followedLeagues = new HashSet<>();
    }

    public User() {
        this.roles = new HashSet<>();
        this.newsPosts = new HashSet<>();
        this.postsComments = new HashSet<>();
        this.followedClubs = new HashSet<>();
        this.followedLeagues = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role newRole) {
        if(!this.roles.contains(newRole)) {
            this.roles.add(newRole);
            newRole.addUser(this);
        }
    }

    public void removeRole(Role role) {
        if(this.roles.contains(role)) {
            this.roles.remove(role);
            role.removeUser(this);
        }
    }

    public Set<NewsPost> getNewsPosts() {
        return newsPosts;
    }

    public void setNewsPosts(Set<NewsPost> newsPosts) {
        this.newsPosts = newsPosts;
    }

    public void addNewsPost(NewsPost post) {
        if(!this.newsPosts.contains(post)) {
            this.newsPosts.add(post);
            post.setAuthor(this);
        }
    }

    public void removeNewsPost(NewsPost post) {
        if(this.newsPosts.contains(post)) {
            this.newsPosts.remove(post);
        }
    }

    public Set<PostComment> getPostsComments() {

        return postsComments;
    }

    public void setPostsComments(Set<PostComment> postsComments) {

        this.postsComments = postsComments;
    }

    public void addPostComment(PostComment comment) {

        if(!this.postsComments.contains(comment)) {

            this.postsComments.add(comment);
            comment.setAuthor(this);
        }
    }

    public void removePostComment(PostComment comment) {

        if(this.postsComments.contains(comment)) {

            this.postsComments.remove(comment);
        }
    }

    public Set<FootballClub> getFollowedClubs() {
        return followedClubs;
    }

    public void setFollowedClubs(Set<FootballClub> followedClubs) {
        this.followedClubs = followedClubs;
    }

    public void addFollowedClub(FootballClub club) {
        if(!this.followedClubs.contains(club)) {
            this.followedClubs.add(club);
            club.addFollower(this);
        }
    }

    public void removeFollowedClub(FootballClub club) {
        if(this.followedClubs.contains(club)) {
            this.followedClubs.remove(club);
        }
    }

    public Set<League> getFollowedLeagues() {
        return followedLeagues;
    }

    public void setFollowedLeagues(Set<League> followedLeagues) {
        this.followedLeagues = followedLeagues;
    }

    public void addFollowedLeague(League league) {
        if(!this.followedLeagues.contains(league)) {
            this.followedLeagues.add(league);
            league.addFollower(this);
        }
    }

    public void removeFollowedLeague(League league) {
        if(this.followedLeagues.contains(league)) {
            this.followedLeagues.remove(league);
        }
    }

    public String getOauth2id() {
        return oauth2id;
    }

    public void setOauth2id(String oauth2id) {
        this.oauth2id = oauth2id;
    }
}