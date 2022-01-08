package org.damian.sak.task.allegrorecruitmenttask.validators;

import org.springframework.stereotype.Component;

@Component
public class JsonValidator {

    /**
     * Validate if JSON is populated with data.
     * @param jsonString JSON to validate
     * @return           true if JSON have information inside or false if JSON don't have any information inside
     */

    public boolean isJsonEmpty(String jsonString){
        return jsonString.equalsIgnoreCase("[]");
    }
}

