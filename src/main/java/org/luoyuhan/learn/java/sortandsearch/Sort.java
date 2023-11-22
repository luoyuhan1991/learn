package org.luoyuhan.learn.java.sortandsearch;

import java.util.*;

public class Sort {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(10);
        list.add(3);
        list.add(2);
        list.add(1);
        list.sort((Comparator.comparingInt(o -> o)));
        System.out.println(list);

        Random random;

        int[] arr = new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1};
        System.out.println(Arrays.toString(arr));
        // 归并排序
        mergeSort(arr);
        System.out.println("归并排序后");
        System.out.println(Arrays.toString(arr));

        // 快排
        arr = new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1};
        System.out.println(Arrays.toString(arr));
        quickSort(arr, 0, arr.length - 1);
        System.out.println("快排后");
        System.out.println(Arrays.toString(arr));
        // 前K快排
        arr = new int[20];
        random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 20; i++) {
            arr[i] = random.nextInt(100);
        }
        System.out.println(Arrays.toString(arr));
        quickSortCut(arr, 0, arr.length - 1, 5);
        System.out.println("前5快排");
        System.out.println(Arrays.toString(arr));

        // 快排找钱K
        arr = new int[20];
        random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 20; i++) {
            arr[i] = random.nextInt(100);
        }
        System.out.println(Arrays.toString(arr));
        System.out.println("快排找前K");
        System.out.println(quickFindK(arr, 0, arr.length - 1, 20));
        System.out.println(Arrays.toString(arr));

        // 快排找一半
        arr = new int[]{1,2,3,2,2,2,5,4,2};

        System.out.println(Arrays.toString(arr));
        System.out.println("快排找一半");
        System.out.println(quickFindK(arr, 0, arr.length - 1, arr.length / 2));
        System.out.println(Arrays.toString(arr));
    }

    public static void mergeSort(int[] src) {
        int[] dest = Arrays.copyOf(src, src.length);
        mergeSortSplit(src, dest, 0, src.length - 1);
    }

    public static void mergeSortSplit(int[] src, int[] dest, int l, int r) {
        if (l < r) {
            // 分治
            int mid = (l + r) / 2;
            mergeSortSplit(src, dest, l, mid);
            mergeSortSplit(src, dest, mid + 1, r);
            // 合并
            mergeSortMerge(src, dest, l, mid, r);
        }
    }

    public static void mergeSortMerge(int[] src, int[] dest, int l, int mid, int r) {
        // 双指针
        int lp = l, rp = mid + 1, op = l;
        // 左右都有数
        while (lp < mid && rp < r) {
            if (src[lp] <= src[rp]) {
                dest[op++] = src[lp++];
            } else {
                dest[op++] = src[rp++];
            }
        }
        // 左侧处理完成
        while (rp <= r) {
            dest[op++] = src[rp++];
        }
        // 右侧处理完成
        while (lp <= mid) {
            dest[op++] = src[lp++];
        }
        // 排过序的元素放入原数组
        while (l <= r) {
            src[l] = dest[l];
            l++;
        }
    }

    public static void quickSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }
        // 从两侧遍历到相遇
        int lp = left, rp = right, flag = nums[left];
        while (lp < rp) {
            // 在右侧找到小于的
            while (lp < rp && nums[rp] >= flag) {
                rp--;
            }
            // 在左侧找到大于的
            while (lp < rp && nums[lp] <= flag) {
                lp++;
            }
            // 交换
            if (lp < rp) {
                int temp = nums[lp];
                nums[lp] = nums[rp];
                nums[rp] = temp;
            }
        }
        // 把标准替换到正确位置
        nums[left] = nums[lp];
        nums[lp] = flag;
        // 分别处理左侧和右侧
        quickSort(nums, left, lp - 1);
        quickSort(nums, lp + 1, right);
    }

    // 利用快排查找topK
    public static long quickFindK(int[] nums, int start, int end, int k) {
        if (start == end) {
            return nums[start];
        }
        int left = start;
        int right = end;
        int pivot = nums[(start + end) / 2];
        while (left <= right) {
            while (left <= right && nums[left] > pivot) {
                left++;
            }
            while (left <= right && nums[right] < pivot) {
                right--;
            }
            if (left <= right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
                right--;
            }
        }
        if (start + k - 1 <= right) {
            return quickFindK(nums, start, right, k);
        }
        if (start + k - 1 >= left) {
            return quickFindK(nums, left, end, k - (left - start));
        }
        return nums[right + 1];
    }

    // 利用堆排序查找topK
    public Integer findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minQueue = new PriorityQueue<>(k);
        for (int num : nums) {
            if (minQueue.size() < k || (!minQueue.isEmpty() && num > minQueue.peek())) {
                minQueue.offer(num);
            }
            if (minQueue.size() > k) {
                minQueue.poll();
            }
        }
        return minQueue.peek();
    }

    // 利用快排只排序前K
    public static void quickSortCut(int[] nums, int left, int right, int k) {
        if (left >= right || k <= 0) {
            return;
        }
        int lp = left, rp = right, key = nums[left], temp;
        while (lp < rp) {
            // 右小, 必须从右侧开始
            while (lp < rp && nums[rp] >= key) {
                rp--;
            }
            // 左大
            while (lp < rp && nums[lp] <= key) {
                lp++;
            }
            // 交换
            if (lp < rp) {
                temp = nums[lp];
                nums[lp] = nums[rp];
                nums[rp] = temp;
            }
        }
        // 归位
        nums[left] = nums[lp];
        nums[lp] = key;

        quickSortCut(nums, left, lp - 1, k);
        if (lp < k) {
            quickSortCut(nums, rp + 1, right, k);
        }
    }
}
