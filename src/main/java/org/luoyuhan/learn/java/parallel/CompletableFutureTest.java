package org.luoyuhan.learn.java.parallel;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {
    public static void main(String[] args) {
        CompletableFuture<String> join = CompletableFuture.supplyAsync(() -> {
            System.out.println("start join");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "ready";
        });
        System.out.println("after join");
        System.out.println(join.join());

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return finalI;
            }).thenAccept(result -> {
                System.out.println(result);
            });
        }
        System.out.println("after accept");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
