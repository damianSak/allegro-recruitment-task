package org.damian.sak.task.allegrorecruitmenttask.utils;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapSorter {

    private MapSorter() {
    }

    /**
     * This method is sorting map pair's of elements according
     * to it's values in descending order.
     * @param mapToSort  unsorted map
     * @return           map sorted according to values inside
     */

    public static Map<String, Integer> sortMapByValuesDescending(Map<String, Integer> mapToSort) {

        LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();

        mapToSort.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

        return reverseSortedMap;
    }

}
