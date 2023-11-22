package org.luoyuhan.learn.java;

import org.luoyuhan.learn.common.Pair;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
// 线程池阻塞队列实现
// MySQL数据存储结构 MyBatis 设计模式
// 双亲委派 机制 类加载器种类
// Java class 文件生成及使用过程  接口中能有实现方法吗 有1.8，default
//
// 字节：类型擦除，进程调度机制，跳跃表及存放数据过程
//
// 2020.10.12 19:30 美团 ES数据一致性 RocketMQ 延迟队列 CPU负载 SQL key_length 大促时遇到的问题 Future并非异步 CompletableFuture如何做到异步
//
// 2020.10.15 11:00 新浪 CAP与数据库（关系型，非关系型）的关系，与zookeeper的关系 百万级秒杀如何实现 监控，无侵入
// 2020.                数据库，行式存储和列式存储 HTTPS过程 跨站的单点登录    java进程内存管理，堆之外  jar包冲突发生在什么时候，编译过程中发生了什么
// 2020.10.26 19:00 百度 动态代理具体实现 k8s docker 基本概念
// 2020.10.29 16:00 腾讯 dubbo 动态无感平滑上线 linux统计命令(awk) 方法级别的运行问题如何定位  MySQL执行效果分析(表的统计信息) binlog
// 2020.11.10 21:00 深拷贝浅拷贝
// 2021.07.28   变量是线程安全的，但是计算过程并不是线程安全 i = i + 1，有三个步骤，1：i 2：i+1 3：i=
//              对应下面的get、+1、put，中间可能导致执行步骤被打乱
//              方式1 使用synchronized 方式2 使用lock
//              线程池获取结果要保证所有相关的线程执行完成，可用线程同步工具countdownlatch或Callable+future
//
// 2021.08.12 20:00 字节飞书 HTTPS，TCP、UDP，字符串编码解析
//
//
//
//
//
//
//

public class LearnMianShi {
    public static void main(String[] args) {

        HashMap<String, Object> map = new HashMap<>(2);
        // 先放再计算大小, 放遇到链表就依次调用equals
        map.put("a", "a");
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>(3);
        // 操作头结点的时候是cas(头结点为空), 往后添加是synchronize头结点
        concurrentHashMap.put("a", "a");
        // size 方法线程不安全
        concurrentHashMap.size();
        concurrentHashMap.putIfAbsent("", "");

        HashMap<Long, Object> longMap = new HashMap<>(16);
        longMap.put(1L, map);
        longMap.put(new Long(1L), map);
        System.out.println(999999999 == new Integer(999999999));

        ReentrantLock lock = new ReentrantLock(true);
        lock.lock();
        lock.tryLock();
        try {
            lock.tryLock(1, TimeUnit.MINUTES);
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        AtomicInteger integer = new AtomicInteger(100);

        CountDownLatch countDownLatch = new CountDownLatch(2);
        try {
            new Thread(() -> {
                countDownLatch.countDown();
                countDownLatch.countDown();
            }).start();
            countDownLatch.await();
            System.out.println("countdown");
        } catch (Exception e) {
            e.printStackTrace();
        }
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        try {
            new Thread(() -> {
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            cyclicBarrier.await();
            System.out.println("cyclicBarrier");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 跳表
        ConcurrentSkipListMap<String, Integer> concurrentSkipListMap = new ConcurrentSkipListMap<>();
        concurrentSkipListMap.put("", 1);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 10, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.getState();
                return thread;
            }
        }, ((r, executor2) -> {
            try {
                executor2.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        ThreadPoolExecutor executor1 = new ThreadPoolExecutor(4, 10, 60, TimeUnit.SECONDS,
                new PriorityBlockingQueue<>(1000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return null;
            }
        }, new ThreadPoolExecutor.AbortPolicy());

        CompletableFuture<Object> completableFuture = CompletableFuture.supplyAsync(() -> "");
        System.out.println(CompletableFuture.supplyAsync(() -> "hello").thenApply((s) -> s + " world").thenApply(s -> s.toUpperCase()).join());

        completableFuture.thenApply((s) -> {
            s = "thenRunAsync";
            System.out.println("thread:" + Thread.currentThread().getName());
            return s;
        });
        System.out.println(completableFuture.join());
        CompletableFuture.runAsync(() -> System.out.println("thread:" + Thread.currentThread().getName()));
        CompletableFuture.runAsync(() -> System.out.println("thread:" + Thread.currentThread().getName()));

        executor.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
        Future<Object> submit = executor.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        });
        try {
            submit.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        RedissonClient redissonClient = Redisson.create();
//        redissonClient.getReadWriteLock("");
//        RLock rLock = redissonClient.getLock("lock");
//        rLock.lock();
//
//        RCountDownLatch rCountDownLatch = redissonClient.getCountDownLatch("countDown");
//        rCountDownLatch.trySetCount(3);

        ArrayList<Integer> integers = IntStream.range(1, 5).boxed().collect(Collectors.toCollection(ArrayList::new));
        integers.remove(1);
        System.out.println(integers.toString());
        integers.remove(new Integer(1));
        System.out.println(integers.toString());

    }

    public String[][] topKstrings (String[] strings, int k) {
        // write code here
        Map<String, Integer> numMap = new HashMap<String, Integer>(strings.length);
        for (String str : strings) {
            Integer num = numMap.get(str);
            if (num == null) {
                numMap.putIfAbsent(str, 1);
            } else {
                numMap.put(str, num + 1);
            }
        }
        List<Pair<String, Integer>> sortList = numMap.entrySet().stream().map(entry -> new Pair<>(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        sortList.sort((Comparator) (o1, o2) -> ((Pair<String, Integer>) o2).getValue() - ((Pair<String, Integer>) o1).getValue() * 10 +
                Integer.valueOf(((Pair<String, Integer>) o2).getKey()) - Integer.valueOf(((Pair<String, Integer>) o1).getKey()));
        String[][] result = new String[2][k];
        for(int i = 0; i < k; i++) {
            result[i][0] = sortList.get(i).getKey();
            result[i][1] = sortList.get(i).getValue().toString();
        }
        return result;
    }
}
