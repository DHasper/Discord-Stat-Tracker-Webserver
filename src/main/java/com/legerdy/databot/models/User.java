package com.legerdy.databot.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="user", uniqueConstraints={@UniqueConstraint(columnNames = {"username", "id"})})
public class User {

    // this is the users Discord ID
    @Id
    @Column(length = 64, nullable=false, unique=true)
    private String id;

    @Column(nullable=false, unique=true)
    private String username;

    private boolean active;

    protected User() {}

    public User(String id, String username, boolean active) {
        this.id = id;
        this.username = username;
        this.active = active;
    }

    /**
     * Getters and setters 
     */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}