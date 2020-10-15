package com.legerdy.databot.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="channel", uniqueConstraints={@UniqueConstraint(columnNames = {"id"})})
public class Channel {
    
    @Id
    @Column(length = 64, nullable=false, unique=true)
    private String id;

    @Column(name = "guild_id", nullable=false)
    private String guildId;

    private String name;

    private enum ChannelType {
        VOICE,
        TEXT,
        CATEGORY,
        NEWS,
        STORE,
        UNKNOWN,
        DM
    }

    private ChannelType type;

    protected Channel() {}

    public Channel(String id, String guildId, String name, String type) {
        this.id = id;
        this.guildId = guildId;
        this.name = name;
        this.type = ChannelType.valueOf(type.toUpperCase());
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

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChannelType getType() {
        return type;
    }

    public void setType(ChannelType type) {
        this.type = type;
    }
}