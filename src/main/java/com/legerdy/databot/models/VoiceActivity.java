package com.legerdy.databot.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="voice_activity")
public class VoiceActivity {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", nullable=false)
    private String userId;

    @Column(name = "channel_id", nullable=false)
    private String channelId;

    @Column(name = "guild_id", nullable=false)
    private String guildId;
    
    @Column(name="log_interval")
    private int interval;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    protected VoiceActivity() {}

    public VoiceActivity(String userId, String channelId, String guildId, int interval) {
        this.userId = userId;
        this.channelId = channelId;
        this.guildId = guildId;
        this.interval = interval;
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

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}