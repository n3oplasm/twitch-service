package com.landmarkstreamers.twitch;

import com.landmarkstreamers.twitch.api.TwitchClient;
import com.landmarkstreamers.twitch.model.Follow;
import com.landmarkstreamers.twitch.model.Stream;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Main class, currently for testing the TwitchClient.
 * @author David B. Lowery
 */
public class Main {

    public static void main(String[] args) throws IOException {
        TwitchClient client = new TwitchClient();

        System.out.println("Follows:\n");

        Iterator<Follow> followIterator = client.getFollows("katarella");

        while (followIterator.hasNext()) {
            System.out.println(followIterator.next().getUser().getDisplayName());
        }

        System.out.println("\nStreams:\n");

        Iterator<Stream> streamIterator = client.getStreams();

        while (streamIterator.hasNext()) {
            System.out.println(streamIterator.next().getChannel().getUrl());
        }
    }

}
