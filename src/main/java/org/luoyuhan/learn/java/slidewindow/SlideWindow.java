package org.luoyuhan.learn.java.slidewindow;

import java.util.HashMap;
import java.util.Map;

public class SlideWindow {
    /* 滑动窗口算法框架 */
//    void slidingWindow(string s, string t) {
//        unordered_map<char, int> need, window;
//        for (char c : t) need[c]++;
//
//        int left = 0, right = 0;
//        int valid = 0;
//        while (right < s.size()) {
//            // c 是将移入窗口的字符
//            char c = s[right];
//            // 右移窗口
//            right++;
//            // 进行窗口内数据的一系列更新
//            ...
//
//            /*** debug 输出的位置 ***/
//            printf("window: [%d, %d)\n", left, right);
//            /********************/
//
//            // 判断左侧窗口是否要收缩
//            while (window needs shrink) {
//                // d 是将移出窗口的字符
//                char d = s[left];
//                // 左移窗口
//                left++;
//                // 进行窗口内数据的一系列更新
//                ...
//            }
//        }
//    }

    // 最小的包含子串
    public static String minContainsSubString(String src, String target) {
        Map<Character, Integer> needs = new HashMap<>(), window = new HashMap<>();
        for (char c : target.toCharArray()) {
            needs.put(c, needs.getOrDefault(c, 0) + 1);
        }
        int left = 0, right = 0;
        int valid = 0;

        int start = 0, end = Integer.MAX_VALUE;

        char[] srcChars = src.toCharArray();
        while (right < srcChars.length) {
            char t = srcChars[right];
            right++;
            if (needs.containsKey(t)) {
                window.put(t, window.getOrDefault(t, 0) + 1);
                if (window.get(t).equals(needs.get(t))) {
                    valid++;
                }
            }
            while (valid == needs.size()) {
                if (right - left < (end -start)) {
                    start = left;
                    end = right;
                }
                t = srcChars[left];
                left++;
                if (needs.containsKey(t)) {
                    if (needs.get(t).equals(window.get(t))) {
                        valid--;
                    }
                    window.put(t, window.get(t) - 1);
                }
            }
        }
        return end == Integer.MAX_VALUE ? "" : src.substring(start, end);
    }

    // 最长不重复子串
    public static String longestNoRepeatSubstring(String src) {
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0;
        char[] chars = src.toCharArray();
        int res = 0;
        int start = 0, end = 0;
        while (right < chars.length) {
            char c = chars[right];
            right++;
            window.put(c, window.getOrDefault(c, 0) + 1);
            while (window.get(c) > 1) {
                char d = chars[left];
                left++;
                window.put(d, window.get(d) - 1);
            }
            if (right - left > res) {
                start = left;
                end = right;
                res = right - left;
            }
        }
        return src.substring(start, end);
    }

    public static void main(String[] args) {
        System.out.println(minContainsSubString("ebbancf", "abc"));
        System.out.println(longestNoRepeatSubstring("abcabcbb"));
        System.out.println(longestNoRepeatSubstring("bbbb"));
        System.out.println(longestNoRepeatSubstring("pwwkew"));
    }
}
