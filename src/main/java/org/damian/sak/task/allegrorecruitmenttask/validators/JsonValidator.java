package org.damian.sak.task.allegrorecruitmenttask.validators;

import org.damian.sak.task.allegrorecruitmenttask.utils.MapSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JsonValidator {

    private static final Logger logger = LoggerFactory.getLogger(MapSorter.class);

    /**
     * Validate if JSON is populated with data.
     *
     * @param jsonString JSON to validate
     * @return true if JSON have information inside or false if JSON don't have any information inside
     */

    public boolean isJsonEmpty(String jsonString){
        logger.info("Validating response from GitHub server");
        return jsonString.equalsIgnoreCase("[]");
    }
}

