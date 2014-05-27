package com.landmarkstreamers.twitch.model;

/**
 * Data model object representation of a Twitch stream.
 *
 * @author David B. Lowery
 */
public class Stream {
    private String game;
    private int viewers;

    private Channel channel;

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getViewers() {
        return viewers;
    }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "Stream{" +
                "game='" + game + '\'' +
                ", viewers=" + viewers +
                ", channel=" + channel +
                '}';
    }
}