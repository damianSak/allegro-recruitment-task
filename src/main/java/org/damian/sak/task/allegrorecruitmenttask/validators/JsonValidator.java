package org.damian.sak.task.allegrorecruitmenttask.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JsonValidator {

    private static final Logger LOG = LoggerFactory.getLogger(JsonValidator.class);

    /**
     * Validate if JSON is populated with data.
     *
     * @param jsonString JSON to validate
     * @return true if JSON have information inside or false if JSON don't have any information inside
     */
    public boolean isJsonEmpty(String jsonString){
        LOG.info("Validating response from GitHub server");
        return jsonString.equalsIgnoreCase("[]");
    }
}

