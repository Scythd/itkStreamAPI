package com.moklyak;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.RecursiveTask;

public class FactorialTask extends RecursiveTask<Integer> {

    private final Integer start;
    private final Integer end;

    public FactorialTask(Integer n) {
        this(1, n);
    }

    private FactorialTask(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start > 1) { // is this task big enough to justify more threading?
            final int half = start + (end - start) / 2;
            final FactorialTask firstHalf = new FactorialTask(start, half);
            final FactorialTask secondHalf = new FactorialTask(half + 1, end);
            firstHalf.fork();
            int secondRes = secondHalf.compute();
            int firstRes = firstHalf.join();
            return firstRes * secondRes;
        } else {
            if (Objects.equals(end, start)) {
                return start;
            } else {
                return end * start;
            }
        }
    }
}
