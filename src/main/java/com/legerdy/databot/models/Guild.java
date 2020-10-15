package com.legerdy.databot.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="guild", uniqueConstraints={@UniqueConstraint(columnNames = {"id", "access_code"})})
public class Guild {

    @Id
    @Column(length = 64, nullable=false, unique=true)
    private String id;

    @Column(name = "icon_url")
    private String iconURL;

    @Column(name = "access_code", unique=true)
    private String accessCode;

    private String name;

    protected Guild() {}

    /**
     * Getters and setters 
     */

    public Guild(String id, String name, String iconURL) {
        this.id = id;
        this.name = name;
        this.iconURL = iconURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

}