package org.luoyuhan.learn.java.multithread;

import java.lang.management.ManagementFactory;

/**
 * @author luoyuhan
 */
public class ThreadStatus {
    // 一、线程5种状态
    //新建状态（New） 新创建了一个线程对象。
    //
    //就绪状态（Runnable） 线程对象创建后，其他线程调用了该对象的start()方法。该状态的线程位于可运行线程池中，变得可运行，等待获取CPU的使用权。
    //
    //运行状态（Running） 就绪状态的线程获取了CPU，执行程序代码。
    //
    //阻塞状态（Blocked） 阻塞状态是线程因为某种原因放弃CPU使用权，暂时停止运行。直到线程进入就绪状态，才有机会转到运行状态。阻塞的情况分三种：
    //
    //等待阻塞：运行的线程执行wait()方法，JVM会把该线程放入等待池中。
    //同步阻塞：运行的线程在获取对象的同步锁时，若该同步锁被别的线程占用，则JVM会把该线程放入锁池中。
    //其他阻塞：运行的线程执行sleep()或join()方法，或者发出了I/O请求时，JVM会把该线程置为阻塞状态。当sleep()状态超时、join()等待线程终止或者超时、或者I/O处理完毕时，线程重新转入就绪状态。
    //死亡状态（Dead）：线程执行完了或者因异常退出了run()方法，该线程结束生命周期。\

    // 二、Jstack中常见的线程状态
    //应用程序启动后，我们对系统运行状况的观测大部分情况下是通过运行日志。但是若某一天发现，日志中记录的行为与预想的不一致，此时需要进一步的系统监控该怎么办，Jstack是常用的排查工具，它能输出在某一个时间，java进程中所有线程的状态，很多时候这些状态信息能给我们的排查工作带来有用的线索。
    //Jstack的输出中，Java线程状态主要是以下几种：
    //
    //RUNNABLE 线程运行中或I/O等待
    //BLOCKED 线程在等待monitor锁(synchronized关键字)
    //TIMED_WAITING 线程在等待唤醒，但设置了时限
    //WAITING 线程在无限等待唤醒
    //这里Jstack使用的关键字描述的线程状态与上一节中线程不太一样，所以可能理解上的可能会出现混淆。虽然Java中的线程一样有上节中描述的5种状态，但在实际情况下线程新建状态和死亡状态持续很短，我们也并不太关心。大多时候我们关注的是运行状态/阻塞状态，这里弄清楚Jstack的输出含义即可。下面用简单的代码产生出以上4中状态。
    public static void main(String[] args) {
        System.out.println(pid());
//        runnable();
//        blocked();
//        timedWaiting();
        waiting();
    }

    public static String pid() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        return name.split("@")[0];
    }

    public static void runnable() {
        long i = 0;
        while (true) {
            i++;
        }
    }

    // 主线程sleep，先让另外一个线程拿到lock，并长期持有lock（sleep会持有锁，wait不会）。此时主线程会BLOCK住等待lock被释放，此时jstack的输出可以看到main线程状态是BLOCKED。这里要注意的是只有synchronized这种方式的锁（monitor锁）才会让线程出现BLOCKED状态，等待ReentrantLock则不会。
    public static void blocked() {
        final Object lock = new Object();
        new Thread() {
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println("i got lock, but don't release");
                    try {
                        Thread.sleep(1000L * 1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }

        synchronized (lock) {
            try {
                Thread.sleep(30 * 1000);
            } catch (InterruptedException e) {
            }
        }
    }

    // 用Lock.tryLock(timeout, timeUnit)，这种方式也会看到TIMED_WAITING状态，这个状态说明线程当前的等待一定是可超时的。
    public static void timedWaiting() {
        final Object lock = new Object();
        synchronized (lock) {
            try {
                lock.wait(30 * 1000);
            } catch (InterruptedException e) {
            }
        }
    }

    // 无超时的等待，必须等待lock.notify()或lock.notifyAll()或接收到interrupt信号才能退出等待状态。同理，ReentrantLock.lock()的无参方法调用，也会使线程状态变成WAITING。
    public static void waiting() {
        final Object lock = new Object();
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
            }
        }
    }
}
