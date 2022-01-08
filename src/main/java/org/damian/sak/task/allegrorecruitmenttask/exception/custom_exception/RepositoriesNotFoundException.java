package org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK)
public class RepositoriesNotFoundException extends Exception {

    public RepositoriesNotFoundException(String message) {
        super(message);
    }
}
