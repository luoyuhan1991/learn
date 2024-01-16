package org.luoyuhan.learn.confirm;

/**
 * @author luoyuhan
 * @since 2023/12/20
 */
public class DeadLock {
    public static void main(String[] args) {
        Money money = new Money();
        Hostage hostage = new Hostage();

        Thread badGuy = new Thread(() -> {
            synchronized (hostage) {
                System.out.println("bad guy: I got " + hostage);
                System.out.println("bad guy: give me " + money + " or I will kill him");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (money) {
                    System.out.println("bad guy: I got the money, I will release " + hostage);
                }
            }
        });
        badGuy.setName("BadGuy");

        Thread police = new Thread(() -> {
            synchronized (money) {
                System.out.println("police: I got " + money);
                System.out.println("police: give me " + hostage + " or I will arrest you");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (hostage) {
                    System.out.println("police: I got " + hostage + ", I will release you");
                }
            }
        });
        police.setName("Police");

        badGuy.start();
        police.start();
    }

    static class Money {
        @Override
        public String toString() {
            return "one million";
        }
    }

    static class Hostage {
        @Override
        public String toString() {
            return "the child of president";
        }
    }
}
