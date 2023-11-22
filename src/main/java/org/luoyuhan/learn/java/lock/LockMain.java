package org.luoyuhan.learn.java.lock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockMain {
    public static final Log log = LogFactory.getLog(LockMain.class);

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 4, 0, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1000), r -> {
            Thread thread = new Thread(r);
            thread.setName("tt");
            return thread;
        }, ((r, executor) -> {
            try {
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        Object o = new Object();

        threadPoolExecutor.submit(() -> {
            System.out.println("1");
            synchronized (o) {
                System.out.println("get o1");
            }
        });
        threadPoolExecutor.submit(() -> {
            System.out.println("2");
            synchronized (o) {
                System.out.println("get o2");
            }
        });

        threadPoolExecutor.submit(() -> {
            lock.lock();
            try {
                System.out.println("3 get lock, then await");
                condition.await();
                System.out.println("3 awoken by 4");
                TimeUnit.SECONDS.sleep(2);
                System.out.println("3 slept 2 seconds");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        threadPoolExecutor.submit(() -> {
            lock.lock();
            try {
                System.out.println("4 get lock, so 3 released lock, then signal 3");
                condition.signal();
                System.out.println("4 signaled");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        threadPoolExecutor.shutdown();

        CountDownLatch latch = new CountDownLatch(2);
        new Thread(latch::countDown).start();
        new Thread(latch::countDown).start();
        try {

            latch.await();
            System.out.println("latch go");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
        readWriteLock.readLock().lock();
        readWriteLock.writeLock().lock();
    }
}
