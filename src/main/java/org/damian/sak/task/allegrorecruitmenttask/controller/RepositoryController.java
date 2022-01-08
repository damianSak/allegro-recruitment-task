package org.damian.sak.task.allegrorecruitmenttask.controller;

import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.RepositoriesNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.UserNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RepositoryController {

    private RepositoryService repositoryService;

    @Autowired
    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping("/repos/{userName}")
    public ResponseEntity<Map<String, Integer>> showReposWithRatings(@PathVariable String userName) throws IOException,
            UserNotFoundException, RepositoriesNotFoundException, URISyntaxException {
        return new ResponseEntity<>(repositoryService.getAllReposWithRatings(userName), HttpStatus.OK);
    }

    @GetMapping("/rating/{userName}")
    public ResponseEntity<Integer> showTotalReposRating(@PathVariable String userName) throws IOException,
            UserNotFoundException, RepositoriesNotFoundException, URISyntaxException {
        return new ResponseEntity<>(repositoryService.getTotalReposRating(userName),HttpStatus.OK);
    }

    @GetMapping("/languages/{userName}")
    public ResponseEntity<Map<String, Integer>> showProgrammingLanguageBySize(@PathVariable String userName)
            throws IOException, UserNotFoundException, RepositoriesNotFoundException, URISyntaxException {
        return new ResponseEntity<>(repositoryService.getProgrammingLanguageRatingBySize(userName),HttpStatus.OK);
    }


}
