package org.luoyuhan.learn.java.multithread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadRunOrderly {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();
        Condition condition4 = lock.newCondition();
        Condition condition5 = lock.newCondition();
        Condition condition6 = lock.newCondition();
        Condition condition7 = lock.newCondition();
        Condition condition8 = lock.newCondition();
        Condition condition9 = lock.newCondition();
        Condition condition10 = lock.newCondition();

        Thread thread1 = new Thread(() -> {
            try {
                System.out.println("thread1 start");
                lock.lock();
                System.out.println("thread1 get lock");
                condition1.await();
                System.out.println("thread1 print 1");
                condition2.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                System.out.println("thread2 start");
                lock.lock();
                System.out.println("thread2 get lock");
                condition2.await();
                System.out.println("thread2 print 2");
                condition3.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        Thread thread3 = new Thread(() -> {
            try {
                System.out.println("thread3 start");
                lock.lock();
                System.out.println("thread3 get lock");
                condition3.await();
                System.out.println("thread3 print 3");
                condition4.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        Thread thread4 = new Thread(() -> {
            try {
                System.out.println("thread4 start");
                lock.lock();
                System.out.println("thread4 get lock");
                condition4.await();
                System.out.println("thread4 print 4");
                condition5.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        Thread thread5 = new Thread(() -> {
            try {
                System.out.println("thread5 start");
                lock.lock();
                System.out.println("thread5 get lock");
                condition5.await();
                System.out.println("thread5 print 5");
                condition6.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        Thread thread6 = new Thread(() -> {
            try {
                System.out.println("thread6 start");
                lock.lock();
                System.out.println("thread6 get lock");
                condition6.await();
                System.out.println("thread6 print 6");
                condition7.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        Thread thread7 = new Thread(() -> {
            try {
                System.out.println("thread7 start");
                lock.lock();
                System.out.println("thread7 get lock");
                condition7.await();
                System.out.println("thread7 print 7");
                condition8.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        Thread thread8 = new Thread(() -> {
            try {
                System.out.println("thread8 start");
                lock.lock();
                System.out.println("thread8 get lock");
                condition8.await();
                System.out.println("thread8 print 8");
                condition9.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        Thread thread9 = new Thread(() -> {
            try {
                System.out.println("thread9 start");
                lock.lock();
                System.out.println("thread9 get lock");
                condition9.await();
                System.out.println("thread9 print 9");
                condition10.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        Thread thread10 = new Thread(() -> {
            try {
                System.out.println("thread10 start");
                lock.lock();
                System.out.println("thread10 get lock");
                condition10.await();
                System.out.println("thread10 print 10");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
        thread9.start();
        thread10.start();
        try {
            Thread.sleep(2000);
            lock.lock();
            condition1.signal();
            System.out.println("condition 1 signal");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        CompletableFuture.runAsync(() -> {
            System.out.println("thread" + Thread.currentThread().getName());
        }).thenRunAsync(() -> {
            System.out.println("thread" + Thread.currentThread().getName());
        });
    }
}
