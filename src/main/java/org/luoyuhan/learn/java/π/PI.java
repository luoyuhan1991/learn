package org.luoyuhan.learn.java.π;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PI {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int coreNum = Runtime.getRuntime().availableProcessors();
        long everyThreadCalculateTime = 10000000L;
        long totalTime = everyThreadCalculateTime * coreNum;
        List<CompletableFuture<Long>> futures = IntStream.range(0, coreNum)
                .mapToObj(i -> CompletableFuture.supplyAsync(() -> countIn(everyThreadCalculateTime)))
                .collect(Collectors.toList());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        long totalInTime = 0;
        for (CompletableFuture<Long> future : futures) {
            totalInTime += future.get();
        }
        System.out.println("in: " + totalInTime + ", all: " + totalTime);
        System.out.println("π: " + ((totalInTime * 4.0) / totalTime));

        calculatePi();
    }

    private static long countIn(long totalTime) {
        long time = 0;
        double x, y;
        for (int i = 0; i < totalTime; i++) {
            x = Math.random();
            y = Math.random();
            if (x * x + y * y < 1) {
                time++;
            }
        }
        return time;
    }

    private static void calculatePi() {
        double x, y;
        Random random = new Random();
        long cycle = 0, rect = 0;
        for (int i = 0; i < 10000000; i++) {
            x = random.nextDouble();
            y = random.nextDouble();
            rect++;
            if (x * x + y * y < 1) {
                cycle++;
            }
        }
        System.out.println("in: " + cycle + ", all: " + rect);
        System.out.println("π: " + ((cycle * 4.0) / rect));
    }
}
