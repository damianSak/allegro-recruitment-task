package org.damian.sak.task.allegrorecruitmenttask.validators;

import org.springframework.stereotype.Component;

@Component
public class JsonValidator {

    public boolean isJsonEmpty(String JsonString){
        return JsonString.equalsIgnoreCase("[]");
    }
}

