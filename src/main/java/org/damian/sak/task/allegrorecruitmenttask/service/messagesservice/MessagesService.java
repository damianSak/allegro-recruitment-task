package org.damian.sak.task.allegrorecruitmenttask.service.messagesservice;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class MessagesService {

    private MessageSource messageSource;

    public String getMessage(String exceptionType){
        return messageSource.getMessage(exceptionType, null, Locale.US);
    }
}
