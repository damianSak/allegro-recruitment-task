package org.damian.sak.task.allegrorecruitmenttask.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ErrorDetails {

    private Date timestamp;
    private String message;
    private String path;
}
