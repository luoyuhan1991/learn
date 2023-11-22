package org.luoyuhan.learn.java.algorithm;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class StringBracketReverse {

    public static void main(String[] args) {
        System.out.println(reverseParentheses("(u(love)i)"));
        System.out.println(reverseParentheses("a(bcdefghijkl(mno)p)q"));
        System.out.println(reverseParentheses("a(c)q"));

        System.out.println(reverse("(u(love)i)"));
        System.out.println(reverse("a(bcdefghijkl(mno)p)q"));
        System.out.println(reverse("a(c)q"));
    }

    public static String reverse(String s) {
        Deque<StringBuilder> stack = new LinkedList<>();
        char ch;
        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);
            if (ch == '(') {
                stack.push(new StringBuilder());
            } else if (ch == ')') {
                StringBuilder pop = stack.pop();
                if (stack.isEmpty()) {
                    stack.push(pop.reverse());
                } else {
                    stack.getFirst().append(pop.reverse());
                }
            } else {
                if (stack.isEmpty()) {
                    stack.push(new StringBuilder().append(ch));
                } else {
                    stack.getFirst().append(ch);
                }
            }
        }
        return stack.getFirst().toString();
    }

    // (u(love)i)
    public static String reverseParentheses(String s) {
        Deque<Character> stack = new LinkedList<>();
        char left = '(', right = ')';

        char ch = 0;
        for (int i = 0; i < s.length();) {
            // 非右括号 入栈
            while (i < s.length() && (ch = s.charAt(i++)) != right) {
                stack.push(ch);
            }
            // 碰到右括号 出栈 再入栈
            if (ch == right) {
                List<Character> characters = new ArrayList<>();
                while (!stack.isEmpty() && (ch = stack.pop()) != left) {
                    characters.add(ch);
                }
                for (Character character : characters) {
                    stack.push(character);
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (!stack.isEmpty()) stringBuilder.insert(0, stack.pop());
        return stringBuilder.toString();
    }

}
