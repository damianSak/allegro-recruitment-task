package org.damian.sak.task.allegrorecruitmenttask.service.repositoryservice;

import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.RepositoriesNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.UserNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.helper.GithubSiteHelper;
import org.damian.sak.task.allegrorecruitmenttask.model.Repository;
import org.damian.sak.task.allegrorecruitmenttask.utils.MapSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class RepositoryServiceImplementation implements RepositoryService {

    private GithubSiteHelper githubSiteHelper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RepositoryServiceImplementation(GithubSiteHelper githubSiteHelper) {
        this.githubSiteHelper = githubSiteHelper;
    }

    @Override
    public Map<String, Integer> getAllReposWithRatings(String userName) throws
            UserNotFoundException, RepositoriesNotFoundException, IOException {
        Map<String, Integer> reposRatingsMap = new HashMap<>();
        List<Repository> repositories = githubSiteHelper.findAllRepositories(userName);
        logger.info("finding repositories for github user: "+userName);
        for (Repository repository : repositories) {
            reposRatingsMap.put(repository.getName(), repository.getRating());
        }
        logger.info("Created map of repositories and it's programming languages");
        return MapSorter.sortMapByValuesDescending(reposRatingsMap);
    }

    @Override
    public int getTotalReposRating(String userName) throws
            UserNotFoundException, RepositoriesNotFoundException, IOException {
        int totalRating = 0;
        List<Repository> repositories = githubSiteHelper.findAllRepositories(userName);
        logger.info("finding repositories for github user: "+userName);
        for (Repository repository : repositories) {
            totalRating += repository.getRating();
        }
        logger.info("Returned total rating of all repositories");
        return totalRating;
    }

    @Override
    public Map<String, Integer> getProgrammingLanguageRatingBySize(String userName) throws
            UserNotFoundException, RepositoriesNotFoundException, IOException {
        List<Repository> repositories = githubSiteHelper.findAllRepositories(userName);
        logger.info("finding repositories for github user: "+userName);
        return sumReposSizeByProgrammingLanguage(repositories);
    }

    private Set<String> createSetOfReposProgrammingLanguages(List<Repository> repositories) {
        Set<String> programmingLanguagesSet = new HashSet<>();
        for (Repository repository : repositories) {
            programmingLanguagesSet.add(repository.getProgrammingLanguage());
        }
        logger.info("Created Set of programming languages ");
        return programmingLanguagesSet;
    }

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
        logger.info("Created map of programming languages and it's total repository sizes on account");
        return MapSorter.sortMapByValuesDescending(mapOfLanguagesBySizes);
    }

}
