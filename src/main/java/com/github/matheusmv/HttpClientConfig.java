package com.github.matheusmv;

import java.net.http.HttpClient;
import java.time.Duration;

public class HttpClientConfig {

    public static HttpClient newHttpClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
    }
}
