package org.luoyuhan.learn.java.ratelimiter;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.StopWatch;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 令牌桶限流
 * 以固定的频率向令牌桶中放入令牌，桶中有令牌则通过
 * 使用队列模拟桶，刷新线程以一定速率放入令牌
 */
public class TokenBucketRateLimiter implements RateLimiter, Runnable {
    // 令牌最大个数，防止一直不消费，一次性来巨量请求时全部通过
    private final int bucketNum;
    // 消费速率, 1秒内放入令牌的数量
    private final int rate;
    // 令牌桶
    private ArrayBlockingQueue<String> tokens;

    /**
     * 构造
     * @param bucketNum 桶个数
     * @param rate 消费速率 1秒内放多少个
     */
    TokenBucketRateLimiter(int bucketNum, int rate) {
        this.bucketNum = bucketNum;
        this.rate = rate;
        init();
    }

    /**
     * 初始化
     */
    void init() {
        tokens = new ArrayBlockingQueue<>(bucketNum);
        // 执行刷新动作的执行器
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        // 每个多少时间放入一个令牌，比如rate=10，即每隔100ms放入一个令牌，线程每隔100毫秒执行一次
        executorService.scheduleAtFixedRate(this, 0, 1000 / rate, TimeUnit.MILLISECONDS);
    }

    public boolean canPass() {
        // 有令牌即可通过
        return StringUtils.isNotBlank(tokens.poll());
    }

    @Override
    public void run() {
        // 令牌桶满了则不继续放入
        if (tokens.remainingCapacity() == 0) {
            return;
        }
        tokens.offer("token");
    }

    //  Redis 实现方式
    //实现方式和漏斗也比较类似，可以使用一个队列保存令牌，一个定时任务用等速率生成令牌放入队列，访问量进入系统时，从队列获取令牌再进入系统。
    //
    //google 开源的 guava 包中的 RateLimiter 类实现了令牌桶算法，不同其实现方式是单机的，集群可以按照上面的实现方式，队列使用中间件 MQ 实现，配合负载均衡算法，考虑集群各个服务器的承压情况做对应服务器的队列是较好的做法。
    //
    //这里简单用 Redis 以及定时任务模拟大概的过程：
    //
    //首先依靠 List 的 leftPop 来获取令牌：
    //
    //// 输出令牌
    //public Response limitFlow() {
    //    Object result = redisTemplate.opsForList().leftPop("limit_list");
    //    if (result == null) {
    //        return Response.ok("当前令牌桶中无令牌！");
    //    }
    //    return Response.ok("访问成功！");
    //}
    //再依靠 Java 的定时任务，定时往 List 中 rightPush 令牌，当然令牌也需要保证唯一性，所以这里利用 UUID 生成：
    //
    //// 10S的速率往令牌桶中添加UUID，只为保证唯一性
    //@Scheduled(fixedDelay = 10_000,initialDelay = 0)
    //public void setIntervalTimeTask(){
    //    redisTemplate.opsForList().rightPush("limit_list",UUID.randomUUID().toString());
    //}

    public static void main(String[] args) {
        TokenBucketRateLimiter rateLimiter = new TokenBucketRateLimiter(1000, 10);

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
