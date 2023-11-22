package org.luoyuhan.learn.java.dynamicprogramming;

import java.util.Arrays;

public class CoinsChange {
    // memo
    // dp array
    public static int coinsChange(int[] coins, int amount) {
        // base case
        if (amount == 0) {
            return 0;
        }
        if (amount < 0) {
            return -1;
        }
        int res = Integer.MAX_VALUE;
        for (int coin : coins) {
            // 子问题结果
            int subProblem = coinsChange(coins, amount - coin);
            // 子问题无结果跳过
            if (subProblem == -1) {
                continue;
            }
            // 子问题+1 与当前最优解比较
            res = Math.min(res, subProblem + 1);
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }
    // dp array
    public static int coinsChangeDp(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        // 外层循环计算所有情况
        for (int i = 0; i < dp.length; i++) {
            // i 即 当前amount
            // 内层循环选择当前情况下(所有子问题)的最小值
            for (int coin : coins) {
                // 子问题无解
                if (i - coin < 0) {
                    continue;
                }
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
        return dp[amount] == (amount + 1) ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        int[] coins = new int[]{1, 2, 5};
        System.out.println(coinsChange(coins, 13));
        System.out.println(coinsChange(coins, 12));
        System.out.println(coinsChange(coins, 11));
        System.out.println(coinsChange(coins, 10));
        System.out.println(coinsChange(coins, 9));
        System.out.println(coinsChangeDp(coins, 13));
        System.out.println(coinsChangeDp(coins, 12));
        System.out.println(coinsChangeDp(coins, 11));
        System.out.println(coinsChangeDp(coins, 10));
        System.out.println(coinsChangeDp(coins, 9));
    }

    // 伪码框架
//    public int coinChange(int[] coins, int amount) {
//        // 题目要求的最终结果是 dp(amount)
//        return dp(coins, amount)
//    }
    // 定义：要凑出金额 n，至少要 dp(coins, n) 个硬币
//    int dp(int[] coins, int n) {
//        // 做选择，选择需要硬币最少的那个结果
//        for (int coin : coins) {
//            res = min(res, 1 + dp(n - coin))
//        }
//        return res
//    }
}
