package org.luoyuhan.learn.java.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolAndPoolStatus {
    // 最佳线程数目 = ( ( 线程等待时间 + 线程CPU时间 ) / 线程CPU时间 ) * CPU数目
    // 即让CPU没有等待时间，把线程等待时间填满，充分利用CPU
    // 下面是简单计算方式
    // IO型线程池，线程数量可提高，2n，因为等待的会比较多，根据线程运行和等待时间计算
    // 计算型线程池，线程数量n+1，因为线程计算快，复用性好
    private static final ExecutorService es = new ThreadPoolExecutor(50, 100, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(100000));

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100000; i++) {
            es.execute(() -> {
                System.out.print(">");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        ThreadPoolExecutor tpe = ((ThreadPoolExecutor) es);

        while (true) {
            System.out.println();

            int queueSize = tpe.getQueue().size();
            System.out.println("当前排队线程数：" + queueSize);

            int activeCount = tpe.getActiveCount();
            System.out.println("当前活动线程数：" + activeCount);

            long completedTaskCount = tpe.getCompletedTaskCount();
            System.out.println("执行完成线程数：" + completedTaskCount);

            long taskCount = tpe.getTaskCount();
            System.out.println("总线程数：" + taskCount);

            Thread.sleep(3000);
        }

    }
}
