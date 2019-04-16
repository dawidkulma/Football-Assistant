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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<NewsPost> newsPosts;

    public User(@Email(message = "*Please provide a valid Email") @NotEmpty(message = "*Please provide an email") String email,
                @Length(min = 5, message = "*Your password must have at least 5 characters")
                @NotEmpty(message = "*Please provide your password") String password,
                @NotEmpty(message = "*Please provide your nickname") String nickname, int active) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.active = active;
        this.roles = new HashSet<>();
        this.newsPosts = new HashSet<>();
    }

    public User() {
        this.roles = new HashSet<>();
        this.newsPosts = new HashSet<>();
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", active=" + active +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                active == user.active &&
                email.equals(user.email) &&
                password.equals(user.password) &&
                Objects.equals(nickname, user.nickname) &&
                roles.equals(user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, nickname, active, roles);
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
}