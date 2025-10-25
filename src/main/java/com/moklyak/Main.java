package com.moklyak;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Order> orders = List.of(
                new Order("Laptop", 1200.0),
                new Order("Smartphone", 800.0),
                new Order("Laptop", 1500.0),
                new Order("Router", 500.0),
                new Order("Smartphone", 1900.0),
                new Order("Laptop", 1500.0),
                new Order("Tablet", 1700.0),
                new Order("Smartphone", 400.0),
                new Order("Laptop", 1200.0),
                new Order("Tablet", 600.0),
                new Order("PC", 11700.0),
                new Order("Router", 400.0),
                new Order("PC", 11200.0),
                new Order("Router", 600.0),
                new Order("Smartphone", 400.0)
        );

        Map<String, List<Order>> grouoped = orders.stream().collect(Collectors.groupingBy(Order::getProduct));

        Map<String, Double> groupedAndTotaled = grouoped.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        x -> x.getValue().stream()
                                .mapToDouble(Order::getCost)
                                .sum()));

        List<Order> groupedAndTotalSorted = groupedAndTotaled.entrySet().stream()
                .map(x -> new Order(x.getKey(), x.getValue()))
                .sorted((x, y) -> Double.compare(y.getCost(), x.getCost()))
                .toList();

        List<Order> groupedAndTotalSortedTop3 = groupedAndTotalSorted.stream().limit(3).toList();

        System.out.println(groupedAndTotalSortedTop3);

    }
}