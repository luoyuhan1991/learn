package org.luoyuhan.learn.java.numandbrackets;

import java.util.Stack;

/**
 * @author luoyuhan
 */
public class NumberBrackets {
    //给定一个经过编码的字符串，返回它解码后的字符串。
    //编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
    //你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
    //此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
    //示例 1：
    //输入：s = "3[a]2[bc]"
    //输出："aaabcbc"
    //示例 2：
    //输入：s = "3[a2[c]]"
    //输出："accaccacc"
    //示例 3：
    //输入：s = "2[abc]3[cd]ef"
    //输出："abcabccdcdcdef"
    //示例 4：
    //输入：s = "abc3[cd]xyz"
    //输出："abccdcdcdxyz"
    public static String print(String str) {
        Stack<String> stack = new Stack<>();
        StringBuilder result = new StringBuilder();

        // 从左至右处理进行栈的操作
        for (int i = 0; i < str.length(); i++) {
            String s = str.substring(i, i + 1);
            // 遇到右括号进行处理
            if (s.equals(right)) {
                // 出栈，直到左括号
                StringBuilder stringBuilder = new StringBuilder();
                while (!(s = stack.pop()).equals(left)) {
                    stringBuilder.append(s);
                }
                // 左括号后处理数字
                int num = 0, times = 1;
                while (!stack.isEmpty()) {
                    if (isNum(s = stack.pop())) {
                        num = times * Integer.parseInt(s) + num;
                        times *= 10;
                    } else {
                        stack.push(s);
                        break;
                    }
                }
                String temp = duplicate(num, stringBuilder.reverse().toString());
                // 处理完成，将结果入栈，给外层继续使用
                for (int j = 0; j < temp.length(); j++) {
                    stack.push(temp.charAt(j) + "");
                }
            } else if (isAbc(s)) {
                stack.push(s);
            } else if (s.equals(left)) {
                stack.push(s);
            } else if (isNum(s)) {
                stack.push(s);
            }
        }
        for (String s : stack) {
            result.append(s);
        }
        return result.toString();
    }

    static String left = "[";
    static String right = "]";

    public static void main(String[] args) {
        System.out.println(print("3[a]2[bc]"));
        System.out.println(print("3[a2[c]]"));
        System.out.println(print("2[abc]3[cd]ef"));
        System.out.println(print("abc3[cd]xyz"));
    }

    public static String duplicate(Integer integer, String s) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < integer; j++) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    public static boolean isNum(String s) {
        return s.charAt(0) >= '0' && s.charAt(0) <= '9';
    }

    public static boolean isAbc(String s) {
        return s.charAt(0) >= 'a' && s.charAt(0) <= 'z';
    }
}
