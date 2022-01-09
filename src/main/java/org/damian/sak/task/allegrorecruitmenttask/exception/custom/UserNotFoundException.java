package org.damian.sak.task.allegrorecruitmenttask.exception.custom;

/**
 * Exception which is thrown when there is no such existing user.
 */
public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {
        super(message);
    }
}
