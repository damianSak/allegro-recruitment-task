package org.damian.sak.task.allegrorecruitmenttask.service.messagesservice;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
@Component
@AllArgsConstructor
public class MessagesService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private MessageSource messageSource;

    public String getMessage(String exceptionType){
        logger.warn("Custom exception message generated");
        return messageSource.getMessage(exceptionType, null, Locale.US);

    }
}
