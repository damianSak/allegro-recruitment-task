package org.damian.sak.task.allegrorecruitmenttask.repository;

import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.RepositoriesNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.UserNotFoundException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;

@Repository
public interface RepositoryRepo {

    Map<String, Integer> getAllReposWithRatings(String userName) throws UserNotFoundException,
            RepositoriesNotFoundException, IOException;

    int getTotalReposRating(String userName) throws UserNotFoundException,
            RepositoriesNotFoundException, IOException;

    Map<String, Integer> getProgrammingLanguageRatingBySize(String userName) throws UserNotFoundException,
            RepositoriesNotFoundException, IOException;

}
