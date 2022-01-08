package org.damian.sak.task.allegrorecruitmenttask.service.repositoryservice;

import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.RepositoriesNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.UserNotFoundException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;

@Repository
public interface RepositoryService {

    Map<String, Integer> getAllReposWithRatings(String userName) throws UserNotFoundException,
            RepositoriesNotFoundException, IOException;

    int getTotalReposRating(String userName) throws UserNotFoundException,
            RepositoriesNotFoundException, IOException;

    Map<String, Integer> getProgrammingLanguageRatingBySize(String userName) throws UserNotFoundException,
            RepositoriesNotFoundException, IOException;

}
