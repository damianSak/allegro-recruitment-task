package org.damian.sak.task.allegrorecruitmenttask.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface RepositoryRepo {

    Map<String, Integer> getAllReposWithRatings(String userName);

    int getTotalReposRating(String userName);

    Map<String, Integer> getProgrammingLanguageRatingBySize(String userName);
}
