package org.damian.sak.task.allegrorecruitmenttask.service.repositoryservice;

import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.RepositoriesNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.UserNotFoundException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;

@Repository
public interface RepositoryServiceInterface {

    /**
     * Method according to given argument is returning a map of repositories with their ratings in descending order.
     *
     * @param username account username
     * @return map of repositories with their ratings
     * @throws UserNotFoundException         if username doesn't exist
     * @throws RepositoriesNotFoundException if user doesn't have any public repositories
     * @throws IOException                   if an input or output exception occurred
     */
    Map<String, Integer> getAllReposWithRatings(String username) throws UserNotFoundException,
            RepositoriesNotFoundException, IOException;

    /**
     * Method according to given argument is counting rating of all public repositories on account.
     *
     * @param username account username
     * @return sum of all repositories rating
     * @throws UserNotFoundException         if username doesn't exist
     * @throws RepositoriesNotFoundException if user doesn't have any public repositories
     * @throws IOException                   if an input or output exception occurred
     */
    int getTotalReposRating(String username) throws UserNotFoundException,
            RepositoriesNotFoundException, IOException;

    /**
     * Method according to given argument is returning a map of programming languages used in repositories with their
     * total size  in
     * descending order.
     *
     * @param username account username
     * @return map of programming languages with their ratings
     * @throws UserNotFoundException         if username doesn't exist
     * @throws RepositoriesNotFoundException if user doesn't have any public repositories
     * @throws IOException                   if an input or output exception occurred
     */
    Map<String, Integer> getProgrammingLanguageRatingBySize(String username) throws UserNotFoundException,
            RepositoriesNotFoundException, IOException;

}
