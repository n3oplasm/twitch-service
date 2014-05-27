package com.landmarkstreamers.twitch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model object representation of a Twitch channel follow.
 *
 * @author David B. Lowery
 */
public class Follow {
    @JsonProperty("created_at")
    private String createdAt;
    private User user;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "createdAt='" + createdAt + '\'' +
                ", user=" + user +
                '}';
    }
}
