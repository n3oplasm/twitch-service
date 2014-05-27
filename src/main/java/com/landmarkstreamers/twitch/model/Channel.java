package com.landmarkstreamers.twitch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URL;

/**
 * Data model object representation of a Twitch channel.
 *
 * @author David B. Lowery
 */
public class Channel {
    public String name;
    @JsonProperty("display_name")
    public String displayName;
    public String status;
    public URL logo;
    public URL url;
    public long views;
    public int followers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public URL getLogo() {
        return logo;
    }

    public void setLogo(URL logo) {
        this.logo = logo;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", status='" + status + '\'' +
                ", logo=" + logo +
                ", url=" + url +
                ", views=" + views +
                ", followers=" + followers +
                '}';
    }
}
