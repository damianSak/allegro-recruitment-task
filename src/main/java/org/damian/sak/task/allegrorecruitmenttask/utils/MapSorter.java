package org.damian.sak.task.allegrorecruitmenttask.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapSorter {

    private static final Logger logger = LoggerFactory.getLogger(MapSorter.class);

    private MapSorter() {
    }

    /**
     * This method is sorting pair's of map elements according
     * to it's values in descending order.
     *
     * @param mapToSort unsorted map
     * @return map sorted according to values inside
     */

    public static Map<String, Integer> sortMapByValuesDescending(Map<String, Integer> mapToSort) {

        LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();

        mapToSort.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        logger.info("Returning map sorted in descending order by it's values");
        return reverseSortedMap;
    }

}
