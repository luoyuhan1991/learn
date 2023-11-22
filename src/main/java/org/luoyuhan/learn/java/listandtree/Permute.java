package org.luoyuhan.learn.java.listandtree;

import java.util.LinkedList;
import java.util.List;

public class Permute {
    public static List<List<Integer>> res = new LinkedList<>();

    // 一组数字的全排列
    public static void permute(int[] nums) {
        // 记录 路径
        LinkedList<Integer> track = new LinkedList<>();
        backtrack(nums, track);
    }

    // 路径：记录在 track 中
    // 选择列表：nums 中不存在于 track 的那些元素
    // 结束条件：nums 中的元素全都在 track 中出现
    public static void backtrack(int[] nums, LinkedList<Integer> track) {
        // 出发结束条件
        if (track.size() == nums.length) {
            res.add(new LinkedList<>(track));
            return;
        }
        for (int num : nums) {
            // 排除不合法的选择
            if (track.contains(num)) {
                continue;
            }
            // 做选择
            track.add(num);
            // 进入下一层决策树
            backtrack(nums, track);
            // 取消选择 回退到上层节点查看下一种可能性
            track.removeLast();
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3};
        permute(nums);
        System.out.println(res);
    }
}
