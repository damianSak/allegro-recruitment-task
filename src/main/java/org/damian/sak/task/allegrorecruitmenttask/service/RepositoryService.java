package org.damian.sak.task.allegrorecruitmenttask.service;

import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.RepositoriesNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.UserNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.helper.GithubSiteHelper;
import org.damian.sak.task.allegrorecruitmenttask.model.Repository;
import org.damian.sak.task.allegrorecruitmenttask.repository.RepositoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class RepositoryService implements RepositoryRepo {

    private GithubSiteHelper githubSiteHelper;

    @Autowired
    public RepositoryService(GithubSiteHelper githubSiteHelper) {
        this.githubSiteHelper = githubSiteHelper;
    }

    @Override
    public Map<String, Integer> getAllReposWithRatings(String userName) throws
            UserNotFoundException, RepositoriesNotFoundException, IOException {
        Map<String, Integer> reposRatingsMap = new HashMap<>();
        List<Repository> repositories = githubSiteHelper.findAllRepositories(userName);
        for (Repository repository : repositories) {
            reposRatingsMap.put(repository.getName(), repository.getRating());
        }
        return reposRatingsMap;
    }

    @Override
    public int getTotalReposRating(String userName) throws
            UserNotFoundException, RepositoriesNotFoundException, IOException {
        int totalRating = 0;
        List<Repository> repositories = githubSiteHelper.findAllRepositories(userName);
        for (Repository repository : repositories) {
            totalRating += repository.getRating();
        }
        return totalRating;
    }

    @Override
    public Map<String, Integer> getProgrammingLanguageRatingBySize(String userName) throws
            UserNotFoundException, RepositoriesNotFoundException, IOException {
        List<Repository> repositories = githubSiteHelper.findAllRepositories(userName);
        return sumReposSizeByProgrammingLanguage(repositories);
    }

    private Set<String> createSetOfReposProgrammingLanguages(List<Repository> repositories) {
        Set<String> programmingLanguagesSet = new HashSet<>();
        for (Repository repository : repositories) {
            programmingLanguagesSet.add(repository.getProgrammingLanguage());
        }
        return programmingLanguagesSet;
    }

    private Map<String, Integer> sumReposSizeByProgrammingLanguage(List<Repository> repositories) {

        Set<String> programmingLanguagesSet = createSetOfReposProgrammingLanguages(repositories);

        Map<String, Integer> mapOfLanguageSizes = new HashMap<>();

        for (String language : programmingLanguagesSet) {
            if (language != null) {
                mapOfLanguageSizes.put(language, 0);
            }
        }

        for (Repository repository : repositories) {
            for (Map.Entry<String, Integer> entry : mapOfLanguageSizes.entrySet()) {
                if (entry.getKey().equals(repository.getProgrammingLanguage())) {
                    int tempValue = entry.getValue() + repository.getSize();
                    mapOfLanguageSizes.put(entry.getKey(), tempValue);
                }
            }
        }
        return mapOfLanguageSizes;
    }

}
