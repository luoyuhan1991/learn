package org.luoyuhan.learn.java.ratelimiter;

import org.springframework.util.StopWatch;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 滑动窗口限流
 * 多个小窗口组成一个大窗口，通过固定频率的操作来刷新窗口数据
 * 实际是通过固定频率的线程池来进行，每一次刷新（获取锁）包括
 * 1. 取下个窗口index = curIndex + 1
 * 2. 将其值记录并置零
 * 3. 将上一步的值从总通过数中减去
 * 4. 窗口后移，即：curIndex = index
 * 限流操作是通过对比当前已通过数量与设置的最大数量来决定
 */
public class SlideWindowRateLimiter implements RateLimiter, Runnable {
    static class Window {
        AtomicInteger windowPassedNum;
        Window () {
            windowPassedNum = new AtomicInteger(0);
        }
    }

    /**
     * 通过数上限
     */
    int limitNum;
    /**
     * 小窗口数量
     */
    int windowNum;
    /**
     * 每个小窗口包含的时间长度
     */
    long windowPeriod;
    /**
     * 每个小窗口包含时间的单位
     */
    TimeUnit timeUnit;

    /**
     * 多个窗口组成的实际窗口
     */
    public Window[] windows;
    /**
     * 窗口index，volatile保证可见
     */
    volatile int windowIndex = 0;
    /**
     * 已通过的数量
     */
    AtomicInteger passedNum;
    /**
     * 获取通行证时保证同步的锁
     */
    ReentrantLock lock = new ReentrantLock();
    // 固定频率刷新窗口数据的执行器
    private final ScheduledExecutorService scheduledExecutorService;

    SlideWindowRateLimiter(int limitNum, int windowNum, long period, TimeUnit timeUnit) {
        this.limitNum = limitNum;
        this.windowNum = windowNum;
        this.windowPeriod = period;
        this.timeUnit = timeUnit;

        passedNum = new AtomicInteger(0);
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        windows = new Window[windowNum];
        for (int i = 0; i < windowNum; i++) {
            windows[i] = new Window();
        }
        scheduledExecutorService.scheduleAtFixedRate(this, this.windowPeriod, this.windowPeriod, this.timeUnit);
    }

    private void stop() {
        scheduledExecutorService.shutdown();
    }

    SlideWindowRateLimiter(int limitNum) {
        this(limitNum, 5, 200, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        // 得到当前窗口的下一个index，取模是为了循环
        int nextIndex = (windowIndex + 1) % windowNum;
        // 获取里面的通过数，再赋值为0
        int num = windows[nextIndex].windowPassedNum.getAndSet(0);
        // 将通过数还给总通过数
        passedNum.addAndGet(-num);
        // 窗口后移
        windowIndex = nextIndex;
    }

    @Override
    public boolean canPass() {
        lock.lock();
        try {
            if (passedNum.get() < limitNum) {
                windows[windowIndex].windowPassedNum.incrementAndGet();
                passedNum.incrementAndGet();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return false;
    }

    public static void main(String[] args) {
        SlideWindowRateLimiter rateLimiter = new SlideWindowRateLimiter(10);

        ExecutorService pool = Executors.newFixedThreadPool(10, r -> {
            Thread thread = new Thread(r);
            thread.setName("exe");
            return thread;
        });
        CountDownLatch countDownLatch = new CountDownLatch(100);
        AtomicInteger passed = new AtomicInteger();
        AtomicInteger canNotPass = new AtomicInteger();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 线程启动完成时间要超过限流器一个周期，不然所有线程一次性执行完成了
        for (int i = 0; i < 100; i++) {
            pool.submit(() -> {
                if (rateLimiter.canPass()) {
                    System.out.println("passed" + passed.incrementAndGet());
                } else {
                    System.out.println("can not pass" + canNotPass.incrementAndGet());
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeSeconds());
        System.out.println(passed.get());
        System.out.println(canNotPass.get());
    }
}
