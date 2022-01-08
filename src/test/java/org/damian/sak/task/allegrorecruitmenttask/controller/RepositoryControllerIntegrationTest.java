package org.damian.sak.task.allegrorecruitmenttask.controller;

import org.damian.sak.task.allegrorecruitmenttask.AllegroRecruitmentTaskApplication;
import org.damian.sak.task.allegrorecruitmenttask.model.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AllegroRecruitmentTaskApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepositoryControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getURL() {
        return "http://localhost:" + port + "/api";
    }

    @Test
    void showReposWithRatings_should_returnNotEmptyResponse_when_properUserGiven() {
        //given
        String testUserName = "allegro";

        //when
        ResponseEntity<Repository> response = restTemplate.getForEntity(getURL() + "/repos/"
                + testUserName, Repository.class);
        //then
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void showReposWithRatings_should_returnNotFoundStatus_when_wrongUserGiven() {
        //given
        String testUserName = "gitHubUserThatShouldRatherNotExist";

        //when
        ResponseEntity<Repository> repositories = restTemplate.getForEntity(getURL() + "/repos/"
                + testUserName, Repository.class);
        //then
        assertNotNull(repositories);
        assertEquals(HttpStatus.NOT_FOUND, repositories.getStatusCode());
    }

    @Test
    void showReposWithRatings_should_returnOKStatus_when_userWithoutPublicReposGiven() {
        //given
        String testUserName = "allegrooo";

        //when
        ResponseEntity<Repository> repositories = restTemplate.getForEntity(getURL() + "/repos/"
                + testUserName, Repository.class);
        //then
        assertNotNull(repositories);
        assertEquals(HttpStatus.OK, repositories.getStatusCode());
    }

    @Test
    void showTotalReposRating_should_returnNotEmptyResponse_when_properUserGiven() {
        //given
        String testUserName = "allegro";

        //when
        ResponseEntity<Integer> response = restTemplate.getForEntity(getURL() + "/rating/"
                + testUserName, Integer.class);
        //then
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void showTotalReposRating_should_returnNotFoundStatus_when_wrongUserGiven() {
        //given
        String testUserName = "gitHubUserThatShouldRatherNotExist";

        //when
        ResponseEntity<Integer> repositories = restTemplate.getForEntity(getURL() + "/rating/"
                + testUserName, Integer.class);
        //then
        assertNotNull(repositories);
        assertEquals(HttpStatus.NOT_FOUND, repositories.getStatusCode());
    }

    @Test
    void showTotalReposRating_should_returnOKStatus_when_userWithoutPublicReposGiven() {
        //given
        String testUserName = "allegrooo";

        //when
        ResponseEntity<Integer> repositories = restTemplate.getForEntity(getURL() + "/rating/"
                + testUserName, Integer.class);
        //then
        assertNotNull(repositories);
        assertEquals(HttpStatus.OK, repositories.getStatusCode());
    }

    @Test
    void showProgrammingLanguageBySize_should_returnNotEmptyResponse_when_properUserGiven() {
        //given
        String testUserName = "allegro";

        //when
        ResponseEntity<Repository> response = restTemplate.getForEntity(getURL() + "/languages/"
                + testUserName, Repository.class);
        //then
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void showProgrammingLanguageBySize_should_returnNotFoundStatus_when_wrongUserGiven() {
        //given
        String testUserName = "gitHubUserThatShouldRatherNotExist";

        //when
        ResponseEntity<Repository> repositories = restTemplate.getForEntity(getURL() + "/languages/"
                + testUserName, Repository.class);
        //then
        assertNotNull(repositories);
        assertEquals(HttpStatus.NOT_FOUND, repositories.getStatusCode());
    }

    @Test
    void showProgrammingLanguageBySize_should_returnOKStatus_when_userWithoutPublicReposGiven() {
        //given
        String testUserName = "allegrooo";

        //when
        ResponseEntity<Repository> repositories = restTemplate.getForEntity(getURL() + "/languages/"
                + testUserName, Repository.class);
        //then
        assertNotNull(repositories);
        assertEquals(HttpStatus.OK, repositories.getStatusCode());
    }
}