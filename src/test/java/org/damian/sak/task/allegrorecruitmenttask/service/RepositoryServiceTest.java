package org.damian.sak.task.allegrorecruitmenttask.service;

import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.RepositoriesNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.UserNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.helper.GithubSiteHelper;
import org.damian.sak.task.allegrorecruitmenttask.model.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RepositoryServiceTest {

    private List<Repository> repoList;

    @Mock
    private GithubSiteHelper githubSiteHelperMock;

    @InjectMocks
    RepositoryService testObject;

    @BeforeEach
    void initialize() {
        this.repoList = new ArrayList<>();
        repoList.add(Repository.builder().name("first").rating(1).programmingLanguage("Java").size(100).build());
        repoList.add(Repository.builder().name("second").rating(2).programmingLanguage("Java").size(200).build());
        repoList.add(Repository.builder().name("third").rating(3).programmingLanguage("Python").size(300).build());
        repoList.add(Repository.builder().name("fourth").rating(4).programmingLanguage("TypeScript").size(400).build());
    }

    @Test
    void getAllReposWithRatings_should_returnReposAndRating_when_properListGiven() throws
            UserNotFoundException, IOException, RepositoriesNotFoundException, URISyntaxException {
        //given
        String testUserName = "User";
        when(githubSiteHelperMock.findAllRepositories(testUserName)).thenReturn(repoList);

        //when
        Map<String, Integer> testMap = testObject.getAllReposWithRatings(testUserName);

        //then
        assertThat(testMap.containsKey("first")).isTrue();
        assertThat(testMap.containsValue(1)).isTrue();
        assertThat(testMap.containsValue(200)).isFalse();
        assertThat(testMap.entrySet().size()).isEqualTo(4);
    }

    @Test
    void getAllReposWithRatings_should_returnNoReposAndRating_when_listIsEmpty() throws
            UserNotFoundException, IOException, RepositoriesNotFoundException, URISyntaxException {
        //given
        repoList.clear();
        String testUserName = "User";
        when(githubSiteHelperMock.findAllRepositories(testUserName)).thenReturn(repoList);

        //when
        Map<String, Integer> testMap = testObject.getAllReposWithRatings(testUserName);

        //then
        assertThat(testMap.entrySet().size()).isZero();
    }

    @Test
    void getTotalReposRating_should_returnTotalRating_when_properListGiven() throws
            UserNotFoundException, IOException, RepositoriesNotFoundException, URISyntaxException {
        //given
        String testUserName = "User";
        when(githubSiteHelperMock.findAllRepositories(testUserName)).thenReturn(repoList);

        //when
        int testRating = testObject.getTotalReposRating(testUserName);
        //then
        assertThat(testRating).isEqualTo(10);
    }

    @Test
    void getTotalReposRating_should_returnZero_when_emptyListGiven() throws
            UserNotFoundException, IOException, RepositoriesNotFoundException, URISyntaxException {
        //given
        repoList.clear();
        String testUserName = "User";
        when(githubSiteHelperMock.findAllRepositories(testUserName)).thenReturn(repoList);
        //when
        int testRating = testObject.getTotalReposRating(testUserName);


        //then
        assertThat(testRating).isZero();
    }

    @Test
    void getProgrammingLanguageRatingBySize_should_returnLanguageRating_when_properListGiven() throws
            UserNotFoundException, IOException, RepositoriesNotFoundException, URISyntaxException {
        //given
        String testUserName = "User";
        when(githubSiteHelperMock.findAllRepositories(testUserName)).thenReturn(repoList);

        //when
        Map<String, Integer> testMap = testObject.getProgrammingLanguageRatingBySize(testUserName);
        //then
        assertThat(testMap.containsKey("Java")).isTrue();
        assertThat(testMap.containsValue(300)).isTrue();
        assertThat(testMap.containsValue(4)).isFalse();
        assertThat(testMap.entrySet().size()).isEqualTo(3);
    }


    @Test
    void getProgrammingLanguageRatingBySize_should_returnNoLanguageRating_when_emptyListGiven() throws
            UserNotFoundException, IOException, RepositoriesNotFoundException, URISyntaxException {
        //given
        repoList.clear();
        String testUserName = "User";
        when(githubSiteHelperMock.findAllRepositories(testUserName)).thenReturn(repoList);

        //when
        Map<String, Integer> testMap = testObject.getProgrammingLanguageRatingBySize(testUserName);
        //then

        assertThat(testMap.entrySet().size()).isZero();
    }

    @Test
    void getProgrammingLanguageRatingBySize_should_returnNoLanguageRating_when_languageIsOnlyNull() throws
            UserNotFoundException, IOException, RepositoriesNotFoundException, URISyntaxException {
        //given
        repoList.clear();
        repoList.add(Repository.builder().name("second").rating(2).programmingLanguage(null).size(200).build());
        String testUserName = "User";
        when(githubSiteHelperMock.findAllRepositories(testUserName)).thenReturn(repoList);

        //when
        Map<String, Integer> testMap = testObject.getProgrammingLanguageRatingBySize(testUserName);

        //then
        assertThat(testMap.containsKey("null")).isFalse();
        assertThat(testMap.containsValue(200)).isFalse();
        assertThat(testMap.entrySet().size()).isZero();
    }

    @Test
    void getProgrammingLanguageRatingBySize_should_returnProperLanguageRating_when_someLanguageAreNull() throws
            UserNotFoundException, IOException, RepositoriesNotFoundException, URISyntaxException {
        //given
        repoList.add(Repository.builder().name("fifth").rating(5).programmingLanguage(null).size(500).build());
        String testUserName = "User";
        when(githubSiteHelperMock.findAllRepositories(testUserName)).thenReturn(repoList);

        //when
        Map<String, Integer> testMap = testObject.getProgrammingLanguageRatingBySize(testUserName);

        //then
        assertThat(testMap.containsKey("null")).isFalse();
        assertThat(testMap.containsValue(500)).isFalse();
        assertThat(testMap.entrySet().size()).isEqualTo(3);
    }
}