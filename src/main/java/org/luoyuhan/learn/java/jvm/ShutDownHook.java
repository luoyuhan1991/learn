package org.luoyuhan.learn.java.jvm;

import java.time.Duration;
import java.time.LocalDateTime;

public class ShutDownHook {
    public static void main(String[] args) {
        System.out.println("main start");
        LocalDateTime time = LocalDateTime.now();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("duration:" + Duration.between(LocalDateTime.now(), time));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("shutdown hook")));
        System.out.println("main exit");
    }
}
