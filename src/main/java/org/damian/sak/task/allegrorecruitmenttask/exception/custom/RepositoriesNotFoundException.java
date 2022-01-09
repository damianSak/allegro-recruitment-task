package org.damian.sak.task.allegrorecruitmenttask.exception.custom;

/**
 * Exception which is thrown when there are no public repositories found for given user.
 */
public class RepositoriesNotFoundException extends Exception {

    public RepositoriesNotFoundException(String message) {
        super(message);
    }
}
