package org.damian.sak.task.allegrorecruitmenttask.utils;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapSorter {

    public static Map<String, Integer> sortMapByValuesDescending(Map<String, Integer> mapToSort) {

        LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();

        mapToSort.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

        return reverseSortedMap;
    }

}
