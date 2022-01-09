package org.damian.sak.task.allegrorecruitmenttask.controller;

import org.damian.sak.task.allegrorecruitmenttask.AllegroRecruitmentTaskApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AllegroRecruitmentTaskApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RepositoryControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private final String EXPECTED_USER_NOT_FOUND_MSG = "GitHub user not found on the server";
    private final String EXPECTED_NO_REPOSITORY_MSG = "GitHub user found but doesn't have any public repository";

    private ParameterizedTypeReference<Map<String, Integer>> responseType =
            new ParameterizedTypeReference<>() {
            };

    private String getURL() {
        return "http://localhost:" + port + "/api";
    }

    @Test
    void showReposWithRatings_should_returnNotEmptyMap_when_properUserGiven() {
        //given
        String testUserName = "allegro";

        //when
        ResponseEntity<Map<String, Integer>> response = restTemplate.exchange(getURL() + "/repos/"
                + testUserName, HttpMethod.GET, null, responseType);

        //then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void showReposWithRatings_should_returnNotFoundStatusAndMessage_when_wrongUserGiven() {
        //given
        String testUserName = "gitHubUserThatHopefullyShouldRatherNotBeExisting";

        //when
        ResponseEntity<Exception> response = restTemplate.getForEntity(getURL() + "/repos/"
                + testUserName, Exception.class);

        //then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(EXPECTED_USER_NOT_FOUND_MSG, response.getBody().getMessage());

    }

    @Test
    void showReposWithRatings_should_returnOKStatusAndMessage_when_userWithoutPublicReposGiven() {
        //given
        String testUserName = "allegrooo";

        //when
        ResponseEntity<Exception> response = restTemplate.getForEntity(getURL() + "/repos/"
                + testUserName, Exception.class);

        //then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(EXPECTED_NO_REPOSITORY_MSG, response.getBody().getMessage());
    }

    @Test
    void showTotalReposRating_should_returnRepositoryRating_when_properUserGiven() {
        //given
        String testUserName = "allegro";

        //when
        ResponseEntity<Integer> response = restTemplate.getForEntity(getURL() + "/rating/"
                + testUserName, Integer.class);

        //then
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody().intValue()).isNotNegative();
    }

    @Test
    void showTotalReposRating_should_returnNotFoundStatusAndMessage_when_wrongUserGiven() {
        //given
        String testUserName = "gitHubUserThatHopefullyShouldRatherNotBeExisting";

        //when
        ResponseEntity<Exception> response = restTemplate.getForEntity(getURL() + "/rating/"
                + testUserName, Exception.class);

        //then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(EXPECTED_USER_NOT_FOUND_MSG, response.getBody().getMessage());
    }

    @Test
    void showTotalReposRating_should_returnOKStatusAndMessage_when_userWithoutPublicReposGiven() {
        //given
        String testUserName = "allegrooo";

        //when
        ResponseEntity<Exception> response = restTemplate.getForEntity(getURL() + "/rating/"
                + testUserName, Exception.class);

        //then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(EXPECTED_NO_REPOSITORY_MSG, response.getBody().getMessage());

    }

    @Test
    void showProgrammingLanguageBySize_should_returnNotEmptyMap_when_properUserGiven() {
        //given
        String testUserName = "allegro";

        //when
        ResponseEntity<Map<String, Integer>> response = restTemplate.exchange(getURL() + "/repos/"
                + testUserName, HttpMethod.GET, null, responseType);

        //then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void showProgrammingLanguageBySize_should_returnNotFoundStatusAndMessage_when_wrongUserGiven() {
        //given
        String testUserName = "gitHubUserThatHopefullyShouldRatherNotBeExisting";

        //when
        ResponseEntity<Exception> response = restTemplate.getForEntity(getURL() + "/languages/"
                + testUserName, Exception.class);

        //then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(EXPECTED_USER_NOT_FOUND_MSG, response.getBody().getMessage());
    }

    @Test
    void showProgrammingLanguageBySize_should_returnOKStatus_when_userWithoutPublicReposGiven() {
        //given
        String testUserName = "allegrooo";

        //when
        ResponseEntity<Exception> response = restTemplate.getForEntity(getURL() + "/languages/"
                + testUserName, Exception.class);

        //then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(EXPECTED_NO_REPOSITORY_MSG, response.getBody().getMessage());
    }
}