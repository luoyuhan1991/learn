package org.luoyuhan.learn.java.multithread;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author luoyuhan
 */
public class ThreadLocalAndThreadSafe {
    public static void main(String[] args) throws InterruptedException {
        // 线程池中使用threadlocal TransmittableThreadLocal
        TransmittableThreadLocal<String> local = new TransmittableThreadLocal<>();
        local.set("我是主线程");

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        // 使用TransmittableThreadLocal包装线程池 即可在线程池中传递threadlocal
        executorService = TtlExecutors.getTtlExecutorService(executorService);
        CountDownLatch c1 = new CountDownLatch(1);
        CountDownLatch c2 = new CountDownLatch(1);

        executorService.submit(() -> {
            System.out.println("我是线程1：" + local.get());
            c1.countDown();
        });
        c1.await();
        local.set("修改主线程");
        System.out.println(local.get());
        executorService.submit(() -> {
            System.out.println("我是线程2：" + local.get());
            c2.countDown();
        });
        c2.await();
        executorService.shutdown();

        AtomicInteger atomicInteger = new AtomicInteger(1);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 10, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000), r -> {
            Thread thread = new Thread(r);
            thread.setName("exe-" + atomicInteger.getAndIncrement());
            return thread;
        }, ((r, executor2) -> {
            try {
                executor2.getQueue().put(r);
                System.out.println("线程池错误1");
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("线程池错误2");
            }
        }));
        List<Future<String>> futures = new ArrayList<>();

        ConcurrentHashMap<String, Long> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("value", 0L);
        ReentrantLock lock = new ReentrantLock();

        long startTime = System.currentTimeMillis();
        for (long i = 0; i < 100000; i++) {
            Callable<String> callable = () -> {
                // 变量是线程安全的，但是计算过程并不是线程安全 i = i + 1，有三个步骤，1：i 2：i+1 3：i=
                // 对应下面的get、+1、put，中间可能导致执行步骤被打乱
                // 方式1 使用synchronized
                synchronized (concurrentHashMap) {
                    concurrentHashMap.put("value", concurrentHashMap.get("value") + 1);
                }

                // 方式2 使用lock
                lock.lock();
                try {
                    concurrentHashMap.put("value", concurrentHashMap.get("value") + 1);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
                return "finished";
            };
            Future<String> future = executor.submit(callable);
            futures.add(future);
        }
        executor.shutdown();
        System.out.println(executor.isShutdown());
        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        System.out.println("task count:" + executor.getCompletedTaskCount());
        System.out.println(concurrentHashMap.get("value"));
        System.out.println("tasks cost:" + (System.currentTimeMillis() - startTime));

        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        threadLocal.set(1);
    }
}
