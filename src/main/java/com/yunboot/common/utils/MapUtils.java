package com.yunboot.common.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapUtils
{
    // 根据map键排序
    public static <K extends Comparable<? super K>, V> Map<K, V> sortByKey(Map<K, V> map, Boolean asc)
    {
        Map<K, V> result = new LinkedHashMap<>();

        map.entrySet().stream()
                .sorted(asc == Boolean.TRUE ? Map.Entry.<K, V> comparingByKey()
                        : Map.Entry.<K, V> comparingByKey().reversed())
                .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));

        return result;
    }

    // 根据map值排序
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, Boolean asc)
    {
        Map<K, V> result = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(asc == Boolean.TRUE ? Map.Entry.<K, V> comparingByValue()
                        : Map.Entry.<K, V> comparingByValue().reversed())
                .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }

    public static void main(String[] args)
    {
        Map<String, Integer> map = new HashMap<>();
        map.put("2018-12-14", 12);
        map.put("2018-12-12", 2);
        map.put("2018-12-10", 34);
        map.put("2018-12-16", 5);
        sortByValue(map, false).forEach((k, v) -> System.out.println(k + ":" + v));
        sortByKey(map, false).forEach((k, v) -> System.out.println(k + ":" + v));
    }
}
