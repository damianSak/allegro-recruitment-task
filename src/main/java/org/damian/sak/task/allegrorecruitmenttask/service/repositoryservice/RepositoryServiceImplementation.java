package org.damian.sak.task.allegrorecruitmenttask.service.repositoryservice;

import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.RepositoriesNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.UserNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.helper.GitHubSiteClient;
import org.damian.sak.task.allegrorecruitmenttask.model.Repository;
import org.damian.sak.task.allegrorecruitmenttask.utils.MapSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class RepositoryServiceImplementation implements RepositoryServiceInterface {

    private GitHubSiteClient gitHubSiteClient;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public RepositoryServiceImplementation(GitHubSiteClient gitHubSiteClient) {
        this.gitHubSiteClient = gitHubSiteClient;
    }

    @Override
    public Map<String, Integer> getAllReposWithRatings(String username) throws
            UserNotFoundException, RepositoriesNotFoundException, IOException {
        Map<String, Integer> reposRatingsMap = new HashMap<>();
        List<Repository> repositories = gitHubSiteClient.findAllRepositories(username);
        LOG.info("Creating map of repositories and it's rating for user: {}", username);
        for (Repository repository : repositories) {
            reposRatingsMap.put(repository.getName(), repository.getRating());
        }
        return MapSorter.sortMapByValuesDescending(reposRatingsMap);
    }

    @Override
    public int getTotalReposRating(String username) throws
            UserNotFoundException, RepositoriesNotFoundException, IOException {
        int totalRating = 0;
        List<Repository> repositories = gitHubSiteClient.findAllRepositories(username);
        LOG.info("Counting total repository rating for user: {}" ,username);
        for (Repository repository : repositories) {
            totalRating += repository.getRating();
        }
        LOG.info("Returned total rating of all repositories");
        return totalRating;
    }


    @Override
    public Map<String, Integer> getProgrammingLanguageRatingBySize(String username) throws
            UserNotFoundException, RepositoriesNotFoundException, IOException {
        List<Repository> repositories = gitHubSiteClient.findAllRepositories(username);
        LOG.info("Creating map of programming languages and it's total size for user: {}", username);
        return sumReposSizeByProgrammingLanguage(repositories);
    }

    /**
     * Method according to given argument is returning a set of programming languages used in repositories.
     *
     * @param repositories list of repositories objects
     * @return set of programming languages
     */
    private Set<String> createSetOfReposProgrammingLanguages(List<Repository> repositories) {
        Set<String> programmingLanguagesSet = new HashSet<>();
        for (Repository repository : repositories) {
            programmingLanguagesSet.add(repository.getProgrammingLanguage());
        }
        LOG.info("Created Set of programming languages for GitHub user ");
        return programmingLanguagesSet;
    }

    /**
     * Method according to given argument is returning a map of programming languages and their total size sorted in
     * descending order.
     *
     * @param repositories list of repositories objects
     * @return map of programming languages and their total size
     */
    private Map<String, Integer> sumReposSizeByProgrammingLanguage(List<Repository> repositories) {
        Set<String> programmingLanguagesSet = createSetOfReposProgrammingLanguages(repositories);
        Map<String, Integer> mapOfLanguagesBySizes = new HashMap<>();

        for (String language : programmingLanguagesSet) {
            if (language != null) {
                mapOfLanguagesBySizes.put(language, 0);
            }
        }
        for (Repository repository : repositories) {
            for (Map.Entry<String, Integer> entry : mapOfLanguagesBySizes.entrySet()) {
                if (entry.getKey().equals(repository.getProgrammingLanguage())) {
                    int tempValue = entry.getValue() + repository.getSize();
                    mapOfLanguagesBySizes.put(entry.getKey(), tempValue);
                }
            }
        }
        return MapSorter.sortMapByValuesDescending(mapOfLanguagesBySizes);
    }

}
