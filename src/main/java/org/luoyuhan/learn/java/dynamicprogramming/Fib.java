package org.luoyuhan.learn.java.dynamicprogramming;

public class Fib {
    public static int fib(int n) {
        if (n <= 2) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }

    public static int fibMemo(int n) {
        int[] memo = new int[n + 1];
        return fibMemoHelper(memo, n);
    }

    public static int fibMemoHelper(int[] memo, int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        if (memo[n] != 0) {
            return memo[n];
        }
        memo[n] = fibMemoHelper(memo, n - 1) + fibMemoHelper(memo, n - 2);
        return memo[n];
    }

    public static int fibMemoArray(int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        int[] arr = new int[n + 1];
        arr[0] = 0;
        arr[1] = 1;
        for (int i = 2; i <= n; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }
        return arr[n];
    }

    public static int fibDp(int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        int pre = 1, cur = 1, sum = 0;
        for (int i = 3; i <= n; i++) {
            sum = pre + cur;
            pre = cur;
            cur = sum;
        }
        return sum;
    }

    public static void main(String[] args) {
        int n = 20;
        long t1 = System.nanoTime();
        System.out.println(fib(n));
        long t2 = System.nanoTime();
        System.out.println("cost:" + (t2 - t1));
        System.out.println(fibMemo(n));
        long t3 = System.nanoTime();
        System.out.println(t3 - t2);
        System.out.println(fibMemoArray(n));
        long t4 = System.nanoTime();
        System.out.println("memo arr cost:" + (t4 - t3));
        System.out.println(fibDp(n));
        long t5 = System.nanoTime();
        System.out.println(t5 - t4);
    }
}
