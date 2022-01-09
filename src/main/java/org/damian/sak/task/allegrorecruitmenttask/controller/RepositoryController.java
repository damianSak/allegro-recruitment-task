package org.damian.sak.task.allegrorecruitmenttask.controller;

import org.damian.sak.task.allegrorecruitmenttask.exception.custom.RepositoriesNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.exception.custom.UserNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.service.repositoryservice.RepositoryServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RepositoryController {

    private RepositoryServiceImpl repositoryServiceImpl;

    public RepositoryController(RepositoryServiceImpl repositoryServiceImpl) {
        this.repositoryServiceImpl = repositoryServiceImpl;
    }

    @GetMapping("/repos/{username}")
    public ResponseEntity<Map<String, Integer>> showReposWithRatings(@PathVariable String username) throws IOException,
            UserNotFoundException, RepositoriesNotFoundException {
        return new ResponseEntity<>(repositoryServiceImpl.getAllReposWithRatings(username), HttpStatus.OK);
    }

    @GetMapping("/rating/{username}")
    public ResponseEntity<Integer> showTotalReposRating(@PathVariable String username) throws IOException,
            UserNotFoundException, RepositoriesNotFoundException {
        return new ResponseEntity<>(repositoryServiceImpl.getTotalReposRating(username),HttpStatus.OK);
    }

    @GetMapping("/languages/{username}")
    public ResponseEntity<Map<String, Integer>> showProgrammingLanguageBySize(@PathVariable String username)
            throws IOException, UserNotFoundException, RepositoriesNotFoundException {
        return new ResponseEntity<>(repositoryServiceImpl.getProgrammingLanguageRatingBySize(username),
                HttpStatus.OK);
    }
}
