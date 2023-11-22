package org.luoyuhan.learn.java.dynamicprogramming;

import org.luoyuhan.learn.model.BinaryTree;

public class HouseRobber {
    // 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，
    // 如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
    //给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
    //示例 1：
    //输入：[1,2,3,1]
    //输出：4
    //解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
    //    偷窃到的最高金额 = 1 + 3 = 4 。
    //示例 2：
    //输入：[2,7,9,3,1]
    //输出：12
    //解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
    //    偷窃到的最高金额 = 2 + 9 + 1 = 12 。
    //
    //来源：力扣（LeetCode）
    //链接：https://leetcode-cn.com/problems/house-robber
    //著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
    public static int houseRobberDp(int[] amounts) {
        if (amounts.length == 0) {
            return 0;
        }
        if (amounts.length == 1) {
            return amounts[0];
        }
        int[] dp = new int[amounts.length];
        dp[0] = amounts[0];
        dp[1] = Math.max(amounts[0], amounts[1]);
        for (int i = 2; i < dp.length; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + amounts[i]);
        }
        return dp[amounts.length - 1];
    }

    public static int houseRobberDpRecurse(int[] amounts, int start) {
        if (start >= amounts.length) {
            return 0;
        }
        return Math.max(houseRobberDpRecurse(amounts, start + 1), houseRobberDpRecurse(amounts, start + 2) + amounts[start]);
    }

    public static int houseRobberNoArr(int[] amounts, int start, int end) {
        int first = amounts[start];
        int second = Math.max(amounts[start], amounts[start + 1]);
        int third = Math.max(first, second);
        for (int i = start + 2; i <= end; i++) {
            third = Math.max(second, first + amounts[i]);
            first = second;
            second = third;
        }
        return third;
    }

    // 2 首尾相连, 拆解成 0到n-2、1到n-1 两个序列比大小
    //输入：nums = [2,3,2]
    //输出：3
    //解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
    //输入：nums = [1,2,3,1]
    //输出：4
    //解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
    //    偷窃到的最高金额 = 1 + 3 = 4 。
    public static int houseRobberLinked(int[] amounts) {
        if (amounts.length == 0) {
            return 0;
        }
        if (amounts.length == 1) {
            return amounts[0];
        }
        return Math.max(houseRobberNoArr(amounts, 0, amounts.length - 2), houseRobberNoArr(amounts, 1, amounts.length - 1));
    }

    //输入: [3,2,3,null,3,null,1]
    //     3
    //    / \
    //   2   3
    //    \   \
    //     3   1
    //输出: 7
    //解释:小偷一晚能够盗取的最高金额= 3 + 3 + 1 = 7.
    //输入: [3,4,5,1,3,null,1]
    //    3
    //    / \
    //   4   5
    //  / \   \
    // 1   3   1
    //
    //输出: 9
    //解释:小偷一晚能够盗取的最高金额= 4 + 5 = 9.
    public static int houseRobberTreeDp(BinaryTree root) {
        if (root == null) {
            return 0;
        }
        int rob = root.val + (root.left == null ? 0 : (houseRobberTreeDp(root.left.left) + houseRobberTreeDp(root.left.right)))
                            + (root.right == null ? 0 : (houseRobberTreeDp(root.right.left) + houseRobberTreeDp(root.right.right)));

        int notRob = houseRobberTreeDp(root.left) + houseRobberTreeDp(root.right);

        return Math.max(rob, notRob);
    }

    public static void main(String[] args) {
        int[] houses1 = {1, 2, 3, 1};
        int[] houses2 = {2, 7, 9, 3, 1};
        int[] houses3 = {2, 3, 2};
        System.out.println(houseRobberDp(houses1));
        System.out.println(houseRobberDp(houses2));
        System.out.println(houseRobberDpRecurse(houses1, 0));
        System.out.println(houseRobberDpRecurse(houses2, 0));
        System.out.println(houseRobberNoArr(houses1, 0, houses1.length - 1));
        System.out.println(houseRobberNoArr(houses2, 0, houses2.length - 1));
        System.out.println();
        System.out.println(houseRobberLinked(houses3));
        System.out.println(houseRobberLinked(houses1));
        System.out.println();
        BinaryTree binaryTree1 = new BinaryTree(3, new BinaryTree(2, null, new BinaryTree(3)), new BinaryTree(3, null, new BinaryTree(1)));
        BinaryTree binaryTree2 = new BinaryTree(3, new BinaryTree(4, new BinaryTree(1), new BinaryTree(3)), new BinaryTree(5, null, new BinaryTree(1)));
        System.out.println(houseRobberTreeDp(binaryTree1));
        System.out.println(houseRobberTreeDp(binaryTree2));
    }
}
