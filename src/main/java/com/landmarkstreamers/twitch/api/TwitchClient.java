package com.landmarkstreamers.twitch.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.landmarkstreamers.twitch.model.Follow;
import com.landmarkstreamers.twitch.model.Stream;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Twitch API client class.
 *
 * @author David B. Lowery
 */
public class TwitchClient {
    public static final String BASE_URL = "https://api.twitch.tv/kraken/";
    public static final String GAME = "Landmark";

    /**
     * Helper method supports HTTP GET requests against the REST webservice
     *
     * @param request the url and query parameters for the get request
     * @return the http response
     * @throws IOException if the service is unreachable
     */
    private String httpGet(String request) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(BASE_URL + request);
        HttpResponse httpResponse = client.execute(httpGet);

        // Get the response
        BufferedReader rd = new BufferedReader
                (new InputStreamReader(httpResponse.getEntity().getContent()));

        StringBuilder response = new StringBuilder();

        String line = "";
        while ((line = rd.readLine()) != null) {
            response.append(line);
        }

        // TODO: does not account for http status 404, 500 etc

        return response.toString();
    }

    /**
     * Obtain an iterator of all live Landmark Twitch streams
     *
     * @return iterator of {@link com.landmarkstreamers.twitch.model.Stream Stream} objects
     */
    public Iterator<Stream> getStreamIterator() {
        return new StreamIterator(GAME);
    }

    /**
     * Obtain an iterator of the followers of a given Twitch channel
     *
     * @param channel the name of the channel
     * @return iterator of {@link com.landmarkstreamers.twitch.model.Follow Follow} objects
     */
    public Iterator<Follow> getFollowIterator(String channel) {
        return new FollowIterator(channel);
    }

    /**
     * The follow iterator implementation class. Encapsulates buffering and web service calls.
     *
     * @see java.util.Iterator
     */
    private class FollowIterator implements Iterator<Follow> {
        private List<Follow> followsList;

        private String channel;
        private ObjectMapper mapper;

        private int total = -1;
        private int cursor = 0;
        private int offset = 0;
        private int limit = 25;

        public FollowIterator(String channel) {
            mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            this.channel = channel;
            bufferFollows(); // initialize from the first page of follows
        }

        @Override
        public boolean hasNext() {
            return cursor < total;
        }

        @Override
        public Follow next() {
            if (cursor < total) {
                if (cursor > 0 && cursor % limit == 0) {
                    // If we have reached the end of a page we must request a new one from the Twitch APIs
                    bufferFollows();
                }

                return followsList.get((cursor++) % limit);
            } else {
                throw new NoSuchElementException("Reached end of follow list.");
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation not supported.");
        }

        /**
         * Using the current limit and offset values this method will make the request to the Twitch APIs to obtain
         * the next page of follows.
         */
        private void bufferFollows() {
            try {
                // The Twitch api for follows takes a value for limit (the max number of results to return per page)
                // and an offset value (where the results begin)

                String queryString = "?limit=" + limit + "&offset=" + offset;
                String request = "channels/" + channel + "/follows" + queryString;
                offset += limit;

                String response = httpGet(request);

                // Now that we have the response, map the json to Java objects

                ObjectNode node = (ObjectNode) new ObjectMapper().readTree(response);

                if (total == -1) {
                    total = node.get("_total").asInt();
                }

                JsonNode follows = node.get("follows");

                followsList = mapper.readValue(new TreeTraversingParser(follows), new TypeReference<List<Follow>>(){});
            } catch (IOException e) {
                throw new IllegalStateException("Failed to buffer follows", e);
            }
        }
    }

    /**
     * The stream iterator implementation class. Encapsulates buffering and web service calls.
     *
     * @see java.util.Iterator
     */
    class StreamIterator implements Iterator<Stream> {
        private List<Stream> streamsList;

        private String game;
        private ObjectMapper mapper;

        private int total = -1;
        private int cursor = 0;
        private int offset = 0;
        private int limit = 25;

        public StreamIterator(String game) {
            mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            this.game = game;
            bufferStreams(); // initialize from the first page of streams
        }

        @Override
        public boolean hasNext() {
            return cursor < total;
        }

        @Override
        public Stream next() {
            if (cursor < total) {
                if (cursor > 0 && cursor % limit == 0) {
                    // If we have reached the end of a page we must request a new one from the Twitch APIs
                    bufferStreams();
                }

                return streamsList.get((cursor++) % limit);
            } else {
                throw new NoSuchElementException("Reached end of stream list.");
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation not supported.");
        }

        /**
         * Using the current limit and offset values this method will make the request to the Twitch APIs to obtain
         * the next page of streams.
         */
        private void bufferStreams() {
            try {
                // The Twitch api for streams takes a value for limit (the max number of results to return per page)
                // and an offset value (where the results begin)

                String request = "streams?game=" + game + "&limit=" + limit + "&offset=" + offset;
                offset += limit;

                String response = httpGet(request);

                // Now that we have the response, map the json to Java objects

                ObjectNode node = (ObjectNode) new ObjectMapper().readTree(response);

                if (total == -1) {
                    total = node.get("_total").asInt();
                }

                JsonNode streams = node.get("streams");

                streamsList = mapper.readValue(new TreeTraversingParser(streams), new TypeReference<List<Stream>>(){});
            } catch (IOException e) {
                throw new IllegalStateException("Failed to buffer streams", e);
            }
        }
    }
}
