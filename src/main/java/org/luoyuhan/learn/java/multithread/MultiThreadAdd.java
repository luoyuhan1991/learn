package org.luoyuhan.learn.java.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

public class MultiThreadAdd {
    public static void main(String[] args) {
        AtomicInteger integer = new AtomicInteger();
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        long time = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            executorService.execute(integer::incrementAndGet);
        }
        long time2 = System.currentTimeMillis();
        System.out.println("cost1: " + (time2 - time));

        LongAdder longAdder = new LongAdder();
        for (int i = 0; i < 1000; i++) {
            executorService.execute(longAdder::increment);
        }
        long time3 = System.currentTimeMillis();
        System.out.println("cost1: " + (time3 - time2));
        executorService.shutdown();
    }

}
