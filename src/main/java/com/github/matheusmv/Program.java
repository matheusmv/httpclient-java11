package com.github.matheusmv;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.time.Duration;

public class Program {

    public static void main(String[] args) throws InterruptedException {
        testAsyncRequest1();
        testAsyncRequest2();
        testAsyncRequest3();
        testAsyncRequest4();
        testAsyncRequest5();
        testAsyncRequest6();

        testRequest1();
        testRequest2();
        testRequest3();
        testRequest4();
        testRequest5();
        testRequest6();

        // wait for asynchronous requests to finish
        Thread.sleep(Duration.ofSeconds(10).toMillis());
    }

    public static void testRequest1() {
        try {
            var status = HttpSyncRequests.getStatusRequest();

            System.out.println("\n\n***REQUEST 1***");
            System.out.println(status.statusCode());
            System.out.println(status.body());
            System.out.println(status.uri());
            System.out.println(status.headers().toString());
            System.out.println(status.version());
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void testRequest2() {
        try {
            var headers = HttpSyncRequests.headRequest();

            System.out.println("\n\n***REQUEST 2***");
            System.out.println(headers.statusCode());
            System.out.println(headers.body());
            System.out.println(headers.uri());

            headers.headers().map().forEach((key, value) -> {
                System.out.printf("%s: %s%n", key, value);
            });

            System.out.println(headers.version());
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void testRequest3() {
        try {
            var response = HttpSyncRequests.getRequestAsString();

            System.out.println("\n\n***REQUEST 3***");
            System.out.println(response.statusCode());
            System.out.println(response.body());
            System.out.println(response.uri());
            System.out.println(response.headers().toString());
            System.out.println(response.version());
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void testRequest4() {
        try {
            final String filePath = Paths.get(".", "src", "main", "resources", "index.html")
                    .toAbsolutePath()
                    .normalize()
                    .toString();

            var response = HttpSyncRequests.getRequestToFile(filePath);

            System.out.println("\n\n***REQUEST 4***");
            System.out.println(response.statusCode());
            System.out.println(response.body());
            System.out.println(response.uri());
            System.out.println(response.headers().toString());
            System.out.println(response.version());
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void testRequest5() {
        try {
            var user = new User("FirstName", "LastName", "user@email.com");

            var response = HttpSyncRequests.postRequestAsString(user);

            System.out.println("\n\n***REQUEST 5***");
            System.out.println(response.statusCode());
            System.out.println(response.body());
            System.out.println(response.uri());
            System.out.println(response.headers().toString());
            System.out.println(response.version());
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void testRequest6() {
        try {
            var response = HttpSyncRequests.redirectRequest();

            System.out.println("\n\n***REQUEST 6***");
            System.out.println(response.statusCode());
            System.out.println(response.body());
            System.out.println(response.uri());
            System.out.println(response.headers().toString());
            System.out.println(response.version());
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void testAsyncRequest1() {
        System.out.println("\n\n***ASYNC REQUEST 1***");
        HttpAsyncRequests.getStatusRequest()
                .thenApply(HttpResponse::statusCode)
                .thenAccept(code -> {
                    System.out.println(code);
                    System.out.println("***ASYNC REQUEST 1 FINISHED***\n\n");
                });
    }

    public static void testAsyncRequest2() {
        System.out.println("\n\n***ASYNC REQUEST 2***");
        HttpAsyncRequests.headRequest()
                .thenApply(HttpResponse::headers)
                .thenAccept(headers -> {
                    headers.map().forEach((key, value) -> {
                        System.out.printf("%s: %s%n", key, value);
                    });
                    System.out.println("***ASYNC REQUEST 2 FINISHED***\n\n");
                });
    }

    public static void testAsyncRequest3() {
        System.out.println("\n\n***ASYNC REQUEST 3***");
        HttpAsyncRequests.getRequestAsString()
                .thenApply(HttpResponse::body)
                .thenAccept(body -> {
                    System.out.println(body);
                    System.out.println("***ASYNC REQUEST 3 FINISHED***\n\n");
                });
    }

    public static void testAsyncRequest4() {
        final String filePath = Paths.get(".", "src", "main", "resources", "index.html")
                .toAbsolutePath()
                .normalize()
                .toString();

        System.out.println("\n\n***ASYNC REQUEST 4***");
        HttpAsyncRequests.getRequestToFile(filePath)
                .thenApply(HttpResponse::statusCode)
                .thenAccept(code -> {
                    System.out.println(code);
                    System.out.println("***ASYNC REQUEST 4 FINISHED***\n\n");
                });
    }

    public static void testAsyncRequest5() {
        var user = new User("FirstName", "LastName", "user@email.com");

        System.out.println("\n\n***ASYNC REQUEST 5***");
        try {
            HttpAsyncRequests.postRequestAsString(user)
                    .thenApply(HttpResponse::body)
                    .thenAccept(body -> {
                        System.out.println(body);
                        System.out.println("***ASYNC REQUEST 5 FINISHED***\n\n");
                    });
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void testAsyncRequest6() {
        System.out.println("\n\n***ASYNC REQUEST 6***");
        HttpAsyncRequests.redirectRequest()
                .thenApply(HttpResponse::statusCode)
                .thenAccept(code -> {
                    System.out.println(code);
                    System.out.println("***ASYNC REQUEST 6 FINISHED***\n\n");
                });
    }
}
