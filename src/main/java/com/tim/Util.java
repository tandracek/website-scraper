package com.tim;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.IntStream;

public class Util {

    public static <T> Map<T, T> mapOf(List<T> items) {
        if (items.size() % 2 != 0) {
            throw new RuntimeException("Invalid number of items in list.  Must be even number.");
        }
        Map<T, T> map = new LinkedHashMap<T, T>();
        IntStream.range(0, items.size()).forEach(index -> {
            map.put(items.get(index), items.get(index + 1));
        });
        return map;
    }
}