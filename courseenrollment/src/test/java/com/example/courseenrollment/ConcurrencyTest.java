package com.example.courseenrollment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConcurrencyTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testConcurrentEnrollments() throws InterruptedException {
        final int threadCount = 10;
        final Long courseId = 1L; 
        
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        // Send concurrent enrollment requests
        IntStream.range(0, threadCount).forEach(i -> {
            executor.submit(() -> {
                String studentName = "Student-" + i;
                String url = "/api/enrollments";
                String requestJson = String.format(
                    "{\"studentName\":\"%s\",\"courseId\":%d}",
                    studentName, courseId);

                ResponseEntity<String> response = restTemplate.postForEntity(
                    url, requestJson, String.class);
                System.out.println("Response: " + response.getBody());
            });
        });

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // Check final course status
        String courseUrl = "/api/courses/" + courseId;
        ResponseEntity<String> courseResponse = restTemplate.getForEntity(
            courseUrl, String.class);
        System.out.println("Final course status: " + courseResponse.getBody());
    }
}
