package org.damian.sak.task.allegrorecruitmenttask.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MapSorter {

    private static final Logger LOG = LoggerFactory.getLogger(MapSorter.class);

    private MapSorter() {
    }

    /**
     * This method is sorting pair's of map elements given as argument, according
     * to it's values in descending order then alphabetical.
     *
     * @param mapToSort unsorted map
     * @return map sorted according to values inside
     */
    public static Map<String, Integer> sortMapByValuesDescending(Map<String, Integer> mapToSort) {
        LinkedHashMap<String, Integer> reverseSortedMap;

        reverseSortedMap = mapToSort.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        LOG.info("Returning Map sorted in descending order by it's values");

        return reverseSortedMap;
    }
}
