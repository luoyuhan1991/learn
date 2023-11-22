package org.luoyuhan.learn.java.jvm;

import java.util.concurrent.atomic.AtomicInteger;

public class SafePoint {
    public static AtomicInteger num = new AtomicInteger(0);

    // https://mp.weixin.qq.com/s/KDUccdLALWdjNBrFjVR74Q
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            // 当循环变量是int时，jvm认为是counted loop, 不会添加safe point
            // long，jvm认为是uncounted loop, 会添加safe point
            for (long i = 0; i < 1000000000; i++) {
                num.getAndAdd(1);
            }
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();
        Thread.sleep(1000);
        System.out.println("num = " + num);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("before main exit, num = " + num)));
        System.out.println("main exit");
    }
}
