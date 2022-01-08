package org.damian.sak.task.allegrorecruitmenttask.controller;

import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.RepositoriesNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.UserNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.model.Repository;
import org.damian.sak.task.allegrorecruitmenttask.service.repositoryservice.RepositoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RepositoryControllerTest {

    private List<Repository> repoList;
    private Map<String, Integer> testMap;

    @Mock
    RepositoryService repositoryServiceMock;

    @InjectMocks
    RepositoryController testObject;

    @BeforeEach
    void initialize() {
        this.repoList = new ArrayList<>();
        this.testMap = new HashMap<>();
        repoList.add(Repository.builder().name("first").rating(1).programmingLanguage("Java").size(100).build());
        repoList.add(Repository.builder().name("second").rating(2).programmingLanguage("Java").size(200).build());
        repoList.add(Repository.builder().name("third").rating(3).programmingLanguage("Python").size(300).build());
        repoList.add(Repository.builder().name("fourth").rating(4).programmingLanguage("TypeScript").size(400).build());
    }

    @Test
    void showReposWithRatings_should_returnListOfReposAndRatings_when_userWithPublicReposIsGiven()
            throws UserNotFoundException, RepositoriesNotFoundException, IOException, URISyntaxException {
        //given
        String testUsername = "Username";
        testMap.put("first", 1);
        testMap.put("second", 2);
        testMap.put("third", 3);
        testMap.put("fourth", 4);

        when(repositoryServiceMock.getAllReposWithRatings(testUsername)).thenReturn(testMap);

        //when
        ResponseEntity<Map<String, Integer>> result = testObject.showReposWithRatings(testUsername);

        //then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody().values().containsAll(testMap.values()));
        assertThat(result.getBody().size()).isEqualTo(4);
    }


    @Test
    void showReposWithRatings_should_throwRepositoriesNotFoundException_when_userWithoutPublicReposIsGiven()
            throws UserNotFoundException, RepositoriesNotFoundException, IOException, URISyntaxException {
        //given
        String testUserName = "Username";
        String expectedMessage = "GitHub user found but doesn't have any public repository";
        when(repositoryServiceMock.getAllReposWithRatings(testUserName)).
                thenThrow(new RepositoriesNotFoundException(expectedMessage));

        //when
        RepositoriesNotFoundException thrown = Assertions.assertThrows(RepositoriesNotFoundException.class, () -> {
            repositoryServiceMock.getAllReposWithRatings(testUserName);
        });

        //then
        Assertions.assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    void showReposWithRatings_should_throwUserNotFoundException_when_wrongUserIsGiven()
            throws UserNotFoundException, RepositoriesNotFoundException, IOException, URISyntaxException {
        //given
        String testUserName = "Username";
        String expectedMessage = "GitHub user not found on the server ";
        when(repositoryServiceMock.getAllReposWithRatings(testUserName)).
                thenThrow(new RepositoriesNotFoundException(expectedMessage));

        //when
        RepositoriesNotFoundException thrown = Assertions.assertThrows(RepositoriesNotFoundException.class, () -> {
            repositoryServiceMock.getAllReposWithRatings(testUserName);
        });

        //then
        Assertions.assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    void showTotalRating_should_returnListOfReposAndRatings_when_userWithPublicReposIsGiven()
            throws UserNotFoundException, RepositoriesNotFoundException, IOException, URISyntaxException {
        //given
        String testUsername = "Username";
        int testResult = 10;

        when(repositoryServiceMock.getTotalReposRating(testUsername)).thenReturn(testResult);
        //when
        ResponseEntity<Integer> result = testObject.showTotalReposRating(testUsername);
        //then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody().intValue()).isEqualTo(testResult);
    }

    @Test
    void showTotalRating_should_throwRepositoriesNotFoundException_when_userWithoutPublicReposIsGiven()
            throws UserNotFoundException, RepositoriesNotFoundException, IOException, URISyntaxException {
        //given
        String testUsername = "Username";
        String expectedMessage = "GitHub user found but doesn't have any public repository";
        when(repositoryServiceMock.getAllReposWithRatings(testUsername)).
                thenThrow(new RepositoriesNotFoundException(expectedMessage));

        //when
        RepositoriesNotFoundException thrown = Assertions.assertThrows(RepositoriesNotFoundException.class, () -> {
            repositoryServiceMock.getAllReposWithRatings(testUsername);
        });

        //then
        Assertions.assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    void showTotalRating_should_throwUserNotFoundException_when_wrongUserIsGiven()
            throws UserNotFoundException, RepositoriesNotFoundException, IOException, URISyntaxException {
        //given
        String testUserName = "Username";
        String expectedMessage = "GitHub user not found on the server ";
        when(repositoryServiceMock.getAllReposWithRatings(testUserName)).
                thenThrow(new RepositoriesNotFoundException(expectedMessage));

        //when
        RepositoriesNotFoundException thrown = Assertions.assertThrows(RepositoriesNotFoundException.class, () -> {
            repositoryServiceMock.getAllReposWithRatings(testUserName);
        });

        //then
        Assertions.assertEquals(expectedMessage, thrown.getMessage());
    }


    @Test
    void showProgrammingLanguageRating_should_returnListOfReposAndRatings_when_userWithPublicReposIsGiven()
            throws UserNotFoundException, RepositoriesNotFoundException, IOException, URISyntaxException {
        //given
        String testUsername = "Username";
        testMap.put("Java", 100);
        testMap.put("Java", 200);
        testMap.put("Python", 300);
        testMap.put("TypeScript", 400);

        when(repositoryServiceMock.getProgrammingLanguageRatingBySize(testUsername)).thenReturn(testMap);
        //when
        ResponseEntity<Map<String, Integer>> result = testObject.showProgrammingLanguageBySize(testUsername);
        //then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody().keySet().containsAll(testMap.keySet()));
        assertThat(result.getBody().size()).isEqualTo(3);
    }

    @Test
    void showProgrammingLanguageRating_should_throwRepositoriesNotFoundException_when_userWithoutPublicReposIsGiven()
            throws UserNotFoundException, RepositoriesNotFoundException, IOException, URISyntaxException {
        //given
        String testUsername = "Username";
        String expectedMessage = "GitHub user found but doesn't have any public repository";
        when(repositoryServiceMock.getAllReposWithRatings(testUsername)).
                thenThrow(new RepositoriesNotFoundException(expectedMessage));

        //when
        RepositoriesNotFoundException thrown = Assertions.assertThrows(RepositoriesNotFoundException.class, () -> {
            repositoryServiceMock.getAllReposWithRatings(testUsername);
        });

        //then
        Assertions.assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    void showProgrammingLanguageRating_should_throwUserNotFoundException_when_wrongUserIsGiven()
            throws UserNotFoundException, RepositoriesNotFoundException, IOException, URISyntaxException {
        //given
        String testUserName = "Username";
        String expectedMessage = "GitHub user not found on the server ";
        when(repositoryServiceMock.getAllReposWithRatings(testUserName)).
                thenThrow(new RepositoriesNotFoundException(expectedMessage));

        //when
        RepositoriesNotFoundException thrown = Assertions.assertThrows(RepositoriesNotFoundException.class, () -> {
            repositoryServiceMock.getAllReposWithRatings(testUserName);
        });

        //then
        Assertions.assertEquals(expectedMessage, thrown.getMessage());
    }
}