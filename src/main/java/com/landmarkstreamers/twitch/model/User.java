package com.landmarkstreamers.twitch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URL;

/**
 * Data model object representation of a Twitch user.
 *
 * @author David B. Lowery
 */
public class User {
    @JsonProperty("display_name")
    private String displayName;
    private String name;
    private String bio;
    private URL logo;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public URL getLogo() {
        return logo;
    }

    public void setLogo(URL logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "User{" +
                "displayName='" + displayName + '\'' +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", logo=" + logo +
                '}';
    }
}
