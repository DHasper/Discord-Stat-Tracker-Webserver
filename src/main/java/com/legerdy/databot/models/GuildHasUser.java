package com.legerdy.databot.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="guild_has_user")
public class GuildHasUser {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", nullable=false)
    private String userId;

    @Column(name = "guild_id", nullable=false)
    private String guildId;

    private String nickname;

    protected GuildHasUser() {}

    public GuildHasUser(String userId, String guildId, String nickname) {
        this.userId = userId;
        this.guildId = guildId;
        this.nickname = nickname;
    }

    /**
     * Getters and setters 
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}