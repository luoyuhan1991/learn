package org.luoyuhan.learn.java.sortandsearch;

public class BinaryFind {
    // 在排序数组中查找元素的第一个位置
    //给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置。
    //你的算法时间复杂度必须是 O(log n) 级别。
    //如果数组中不存在目标值，返回 -1。

    //示例 1:
    //输入: nums = [5,7,7,8,8,10], target = 8
    //输出:3

    //示例 2:
    //输入: nums = [5,7,7,8,8,10], target = 6
    //输出:-1
    public static int binarySearchRecurse(int[] nums, int target, int left, int right) {
        if (left < 0 || right == nums.length) {
            return -1;
        }
        if (left == right && nums[left] != target) {
            return -1;
        }
        int mid = (left + right) / 2;
        if (nums[mid] == target) {
            // 重点：有重复值，线性查找左边界或右边界
            do {
                mid -= 1;
            } while (nums[mid] == target);
            return mid + 1;
        }
        // 目标在右侧
        if (nums[mid] < target) {
            return binarySearchRecurse(nums, target, mid + 1, right);
        }
        // 目标在左侧
        if (nums[mid] > target) {
            return binarySearchRecurse(nums, target, left, mid - 1);
        }
        return -1;
    }

    public static int binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        // 搜索区间为 [left, right]
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                // 搜索区间变为 [mid+1, right]
                left = mid + 1;
            } else if (nums[mid] > target) {
                // 搜索区间变为 [left, mid-1]
                right = mid - 1;
            } else if (nums[mid] == target) {
                // 收缩右侧边界
                right = mid - 1;
            }
        }

        // 检查出界情况, 退出条件只有left = right + 1, 就看left是否越界
        if (left >= nums.length || nums[left] != target) {
            return -1;
        }
        return left;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{5,7,7,8,8,10};
        System.out.println(binarySearchRecurse(nums, 8, 0, nums.length - 1));
        System.out.println(binarySearchRecurse(nums, 6, 0, nums.length - 1));
        System.out.println(binarySearch(nums, 8));
        System.out.println(binarySearch(nums, 6));
    }
}
