package org.damian.sak.task.allegrorecruitmenttask.controller;

import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.RepositoriesNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.UserNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.service.repositoryservice.RepositoryServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
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

    private RepositoryServiceImplementation repositoryServiceImplementation;

    @Autowired
    public RepositoryController(RepositoryServiceImplementation repositoryServiceImplementation) {
        this.repositoryServiceImplementation = repositoryServiceImplementation;
    }

    @GetMapping("/repos/{userName}")
    public ResponseEntity<Map<String, Integer>> showReposWithRatings(@PathVariable String userName) throws IOException,
            UserNotFoundException, RepositoriesNotFoundException {
        return new ResponseEntity<>(repositoryServiceImplementation.getAllReposWithRatings(userName), HttpStatus.OK);
    }

    @GetMapping("/rating/{userName}")
    public ResponseEntity<Integer> showTotalReposRating(@PathVariable String userName) throws IOException,
            UserNotFoundException, RepositoriesNotFoundException {
        return new ResponseEntity<>(repositoryServiceImplementation.getTotalReposRating(userName),HttpStatus.OK);
    }

    @GetMapping("/languages/{userName}")
    public ResponseEntity<Map<String, Integer>> showProgrammingLanguageBySize(@PathVariable String userName)
            throws IOException, UserNotFoundException, RepositoriesNotFoundException {
        return new ResponseEntity<>(repositoryServiceImplementation.getProgrammingLanguageRatingBySize(userName),HttpStatus.OK);
    }


}
