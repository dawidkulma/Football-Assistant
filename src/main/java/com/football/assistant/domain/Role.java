package com.football.assistant.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private int id;

    @Column(name = "role", length = 512)
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role(String role) {
        this.role = role;
        this.users = new HashSet<>();
    }

    public Role() {
        this.users = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        if(!this.users.contains(user)) {
            this.users.add(user);
            user.addRole(this);
        }
    }

    public void removeUser(User user) {
        if(this.users.contains(user)) {
            this.users.remove(user);
            user.removeRole(this);
        }
    }
}
