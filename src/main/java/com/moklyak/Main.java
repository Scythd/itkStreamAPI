package com.moklyak;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        // First task
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


        // second task
        List<Student> students = Arrays.asList(
                new Student("Student1", Map.of("Math", 90, "Physics", 85)),
                new Student("Student2", Map.of("Math", 95, "Physics", 88)),
                new Student("Student3", Map.of("Math", 88, "Chemistry", 92)),
                new Student("Student4", Map.of("Physics", 78, "Chemistry", 85))
        );

        Map<String, Double> result = students.stream()
                .parallel()
                .flatMap(x -> x.getGrades().entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey))
                .entrySet().stream()
                .map(x -> new AbstractMap.SimpleEntry<>(x.getKey(), x.getValue().stream().mapToInt(Map.Entry::getValue).average().orElseThrow()))
                .collect(Collectors.toMap(Map.Entry::getKey, AbstractMap.SimpleEntry::getValue));

        System.out.println(result);

        //third task
        Integer n = 10;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        FactorialTask factorialTask = new FactorialTask(n);
        Integer fjpRes = forkJoinPool.invoke(factorialTask);
        System.out.println("Факториал " + n + "! = " + fjpRes);

        n = 9;
        forkJoinPool = new ForkJoinPool();
        factorialTask = new FactorialTask(n);
        fjpRes = forkJoinPool.invoke(factorialTask);
        System.out.println("Факториал " + n + "! = " + fjpRes);

        n = 11;
        forkJoinPool = new ForkJoinPool();
        factorialTask = new FactorialTask(n);
        fjpRes = forkJoinPool.invoke(factorialTask);
        System.out.println("Факториал " + n + "! = " + fjpRes);

    }
}