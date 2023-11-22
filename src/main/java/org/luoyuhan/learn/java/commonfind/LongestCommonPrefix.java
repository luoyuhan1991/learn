package org.luoyuhan.learn.java.commonfind;

public class LongestCommonPrefix {
    // 编写一个函数来查找字符串数组中的最长公共前缀。
    //如果不存在公共前缀，返回空字符串""。
    //示例 1：
    //输入：strs = ["flower","flow","flight"]
    //输出："fl"
    //示例 2：
    //输入：strs = ["dog","racecar","car"]
    //输出：""
    //解释：输入不存在公共前缀。

    public static void main(String[] args) {
        String[] strings = new String[]{"flower", "flow", "flight"};
        System.out.println(longestCommonPrefix1(strings));
        strings = new String[]{"dog", "racecar", "car"};
        System.out.println(longestCommonPrefix1(strings));
        strings = new String[]{"ab", "a"};
        System.out.println(longestCommonPrefix1(strings));
    }

    public static String longestCommonPrefix(String[] strs) {
        if (strs.length == 1) {
            return strs[0];
        }
        int minLength = strs[0].length();
        for (String str : strs) {
            minLength = Math.min(minLength, str.length());
        }
        String longest = "";
        for (int i = 1; i <= minLength; i++) {
            String subStr = strs[0].substring(0, i);
            boolean flag = true;
            for (int j = 1; j < strs.length; j++) {
                if (!strs[j].startsWith(subStr)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                longest = subStr;
            }
        }
        return longest;
    }

    // 公共的，从最早的两个开始找公共的，不符合就-1，不用考虑已比较的
    public static String longestCommonPrefix1(String[] strs) {
        String prefix = strs[0];
        for (String str : strs) {
            while (!str.startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if ("".equals(prefix)) {
                    return "";
                }
            }
        }
        return prefix;
    }
}
