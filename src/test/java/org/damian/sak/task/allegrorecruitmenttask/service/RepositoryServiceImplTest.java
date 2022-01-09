package org.damian.sak.task.allegrorecruitmenttask.service;

import org.damian.sak.task.allegrorecruitmenttask.exception.custom.RepositoriesNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.exception.custom.UserNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.helper.GitHubSiteClient;
import org.damian.sak.task.allegrorecruitmenttask.model.Repository;
import org.damian.sak.task.allegrorecruitmenttask.service.repositoryservice.RepositoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RepositoryServiceImplTest {

    private List<Repository> repoList;
    private String testUsername;

    @Mock
    private GitHubSiteClient gitHubSiteClientMock;

    @InjectMocks
    RepositoryServiceImpl testObject;

    @BeforeEach
    void initialize() {
        this.testUsername = "User";
        this.repoList = new ArrayList<>();
        repoList.add(Repository.builder().name("first").rating(1).programmingLanguage("Java").size(100).build());
        repoList.add(Repository.builder().name("second").rating(2).programmingLanguage("Java").size(200).build());
        repoList.add(Repository.builder().name("third").rating(3).programmingLanguage("Python").size(300).build());
        repoList.add(Repository.builder().name("fourth").rating(4).programmingLanguage("TypeScript").size(400).build());
    }

    @Test
    void getAllReposWithRatings_should_returnReposAndRatingInOrder_when_properListGiven() throws
            UserNotFoundException, IOException, RepositoriesNotFoundException {
        //given
        when(gitHubSiteClientMock.findAllRepositories(testUsername)).thenReturn(repoList);

        //when
        Map<String, Integer> testMap = testObject.getAllReposWithRatings(testUsername);

        //then
        assertThat(testMap.containsValue(200)).isFalse();
        assertThat(testMap.entrySet().size()).isEqualTo(4);
        assertThat(testMap.keySet().toArray()[0]).isEqualTo("fourth");
        assertThat(testMap.values().toArray()[0]).isEqualTo(4);
        assertThat(testMap.keySet().toArray()[3]).isEqualTo("first");
        assertThat(testMap.values().toArray()[3]).isEqualTo(1);
    }

    @Test
    void getAllReposWithRatings_should_returnNoReposAndRating_when_listIsEmpty() throws
            UserNotFoundException, IOException, RepositoriesNotFoundException {
        //given
        repoList.clear();
        when(gitHubSiteClientMock.findAllRepositories(testUsername)).thenReturn(repoList);

        //when
        Map<String, Integer> testMap = testObject.getAllReposWithRatings(testUsername);

        //then
        assertThat(testMap.entrySet().size()).isZero();
    }

    @Test
    void getTotalReposRating_should_returnTotalRating_when_properListGiven() throws
            UserNotFoundException, IOException, RepositoriesNotFoundException {
        //given
        when(gitHubSiteClientMock.findAllRepositories(testUsername)).thenReturn(repoList);

        //when
        int testRating = testObject.getTotalReposRating(testUsername);

        //then
        assertThat(testRating).isEqualTo(10);
    }

    @Test
    void getTotalReposRating_should_returnZero_when_emptyListGiven() throws
            UserNotFoundException, IOException, RepositoriesNotFoundException {
        //given
        repoList.clear();
        when(gitHubSiteClientMock.findAllRepositories(testUsername)).thenReturn(repoList);

        //when
        int testRating = testObject.getTotalReposRating(testUsername);

        //then
        assertThat(testRating).isZero();
    }

    @Test
    void getProgrammingLanguageRatingBySize_should_returnLanguageRatingInOrder_when_properListGiven() throws
            UserNotFoundException, IOException, RepositoriesNotFoundException {
        //given
        when(gitHubSiteClientMock.findAllRepositories(testUsername)).thenReturn(repoList);

        //when
        Map<String, Integer> testMap = testObject.getProgrammingLanguageRatingBySize(testUsername);

        //then
      assertThat(testMap.keySet().toArray()[0]).isEqualTo("TypeScript");
       assertThat(testMap.values().toArray()[0]).isEqualTo(400);
        assertThat(testMap.keySet().toArray()[2]).isEqualTo("Python");
        assertThat(testMap.values().toArray()[2]).isEqualTo(300);
        assertThat(testMap.entrySet().size()).isEqualTo(3);
    }

    @Test
    void getProgrammingLanguageRatingBySize_should_returnNoLanguageRating_when_emptyListGiven() throws
            UserNotFoundException, IOException, RepositoriesNotFoundException {
        //given
        repoList.clear();
        when(gitHubSiteClientMock.findAllRepositories(testUsername)).thenReturn(repoList);

        //when
        Map<String, Integer> testMap = testObject.getProgrammingLanguageRatingBySize(testUsername);

        //then
        assertThat(testMap.entrySet().size()).isZero();
    }

    @Test
    void getProgrammingLanguageRatingBySize_should_returnNoLanguageRating_when_languageIsOnlyNull() throws
            UserNotFoundException, IOException, RepositoriesNotFoundException {
        //given
        repoList.clear();
        repoList.add(Repository.builder().name("second").rating(2).programmingLanguage(null).size(200).build());
        when(gitHubSiteClientMock.findAllRepositories(testUsername)).thenReturn(repoList);

        //when
        Map<String, Integer> testMap = testObject.getProgrammingLanguageRatingBySize(testUsername);

        //then
        assertThat(testMap.containsKey("null")).isFalse();
        assertThat(testMap.containsValue(200)).isFalse();
        assertThat(testMap.entrySet().size()).isZero();
    }

    @Test
    void getProgrammingLanguageRatingBySize_should_returnProperLanguageRating_when_someLanguageAreNull() throws
            UserNotFoundException, IOException, RepositoriesNotFoundException {
        //given
        repoList.add(Repository.builder().name("sixth").rating(6).programmingLanguage(null).size(600).build());
        when(gitHubSiteClientMock.findAllRepositories(testUsername)).thenReturn(repoList);

        //when
        Map<String, Integer> testMap = testObject.getProgrammingLanguageRatingBySize(testUsername);

        //then
        assertThat(testMap.keySet().toArray()[0]).isEqualTo("TypeScript");
        assertThat(testMap.values().toArray()[0]).isEqualTo(400);
        assertThat(testMap.keySet().toArray()[2]).isEqualTo("Python");
        assertThat(testMap.values().toArray()[2]).isEqualTo(300);
        assertThat(testMap.containsKey("null")).isFalse();
        assertThat(testMap.containsValue(600)).isFalse();
        assertThat(testMap.entrySet().size()).isEqualTo(3);
    }
}