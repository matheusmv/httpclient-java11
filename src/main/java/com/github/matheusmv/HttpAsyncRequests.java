package com.github.matheusmv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class HttpAsyncRequests {

    private static final String BASE_URL = "https://httpbin.org";
    private static final String GET_ENDPOINT = BASE_URL + "/get";
    private static final String POST_ENDPOINT = BASE_URL + "/post";
    private static final String REDIRECT_ENDPOINT = BASE_URL + "/redirect/3";

    private static final HttpClient client = HttpClientConfig.newHttpClient();

    public static CompletableFuture<HttpResponse<Void>> getStatusRequest() {
        HttpRequest request = HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(3))
                .uri(URI.create(GET_ENDPOINT))
                .GET()
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.discarding());
    }

    public static CompletableFuture<HttpResponse<Void>> headRequest() {
        HttpRequest request = HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(3))
                .uri(URI.create(GET_ENDPOINT))
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.discarding());
    }

    public static CompletableFuture<HttpResponse<String>> getRequestAsString() {
        HttpRequest request = HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(3))
                .uri(URI.create(GET_ENDPOINT))
                .GET()
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    }

    public static CompletableFuture<HttpResponse<Path>> getRequestToFile(String filePath) {
        Objects.requireNonNull(filePath);

        HttpRequest request = HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(3))
                .uri(URI.create(GET_ENDPOINT))
                .GET()
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofFile(Paths.get(filePath)));
    }

    public static CompletableFuture<HttpResponse<String>> postRequestAsString(User userDetails) throws JsonProcessingException {
        Objects.requireNonNull(userDetails);

        Map<String, String> userRequest = new HashMap<>() {{
            put("first_name", userDetails.getFirstName());
            put("last_name", userDetails.getLastName());
            put("email", userDetails.getLastName());
        }};

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(userRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(3))
                .uri(URI.create(POST_ENDPOINT))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    public static CompletableFuture<HttpResponse<Void>> redirectRequest() {
        HttpRequest request = HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(3))
                .uri(URI.create(REDIRECT_ENDPOINT))
                .GET()
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.discarding());
    }
}
