package com.legerdy.databot.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="message", uniqueConstraints={@UniqueConstraint(columnNames = {"id"})})
public class Message {
    
    @Id
    @Column(length = 64, nullable=false, unique=true)
    private String id;

    @Column(name = "user_id", nullable=false)
    private String userId;

    @Column(name = "channel_id", nullable=false)
    private String channelId;

    @Column(name = "guild_id", nullable=false)
    private String guildId;

    @Column(length = 2010)
    private String content;

    private float typingTime;

    private boolean meme;

    private boolean hasFile;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    protected Message() {}

    public Message(String id, String userId, String channelId, String guildId, String content, float typingTime, boolean meme, boolean hasFile) {
        this.id = id;
        this.userId = userId;
        this.channelId = channelId;
        this.guildId = guildId;
        this.content = content;
        this.typingTime = typingTime;
        this.meme = meme;
        this.hasFile = hasFile;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getTypingTime() {
        return typingTime;
    }

    public void setTypingTime(float typingTime) {
        this.typingTime = typingTime;
    }

    public boolean isMeme() {
        return meme;
    }

    public void setMeme(boolean meme) {
        this.meme = meme;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isHasFile() {
        return hasFile;
    }

    public void setHasFile(boolean hasFile) {
        this.hasFile = hasFile;
    }

}