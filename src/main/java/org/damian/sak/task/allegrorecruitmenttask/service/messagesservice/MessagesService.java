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
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final static Locale DEFAULT_LOCALE = Locale.US;

    private MessageSource messageSource;

    /**
     * This method is taking a proper custom exception message according to type of occurring exception
     * during server work after communicating with GitHub server.
     *
     * @param exceptionType type of exception according to custom exceptions
     * @return custom exception message from messages.properties file in resource folder
     */
    public String getMessage(String exceptionType) {
        LOG.info("Custom exception message loaded for server response");
        return messageSource.getMessage(exceptionType, null, DEFAULT_LOCALE);
    }
}
