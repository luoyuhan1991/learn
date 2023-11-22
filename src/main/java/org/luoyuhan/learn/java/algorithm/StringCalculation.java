package org.luoyuhan.learn.java.algorithm;

import java.util.Stack;

public class StringCalculation {
    public static int plusAndMinus(String str) {
        int res = 0;
        int tempNum = 0;
        Integer calFlag = null;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= '0' && c <= '9') {
                tempNum = tempNum * 10 + (c - '0');
            } else {
                if (calFlag != null) {
                    res = calFlag == 1 ? res + tempNum : res - tempNum;
                } else {
                    res = tempNum;
                }
                calFlag = c == '+' ? 1 : 0;
                tempNum = 0;
            }
        }
        if (calFlag != null) {
            res = calFlag == 1 ? res + tempNum : res - tempNum;
        } else {
            res = tempNum;
        }
        return res;
    }

    // 1+2*3/4-5
    public static double plusMinusMultiDivide(String str) {
        double res = 0;
        Double tempNum = 0d;
        Stack<Double> nums = new Stack<>();
        Stack<Character> opers = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= '0' && c <= '9') {
                tempNum = tempNum * 10 + (c - '0');
            } else {
                opers.push(c);
                nums.push(tempNum);
                tempNum = 0d;
            }
        }
        nums.push(tempNum);
        // 1+2*3/4-5
        // 1 2 3 4 5
        // +*/-
        while (!opers.isEmpty()) {
            Character oper1 = opers.pop();
            double resTemp = 0;
            if (opers.isEmpty()) {
                // 只剩一个操作符，直接计算
                double num1 = nums.pop();
                double num2 = nums.pop();
                switch (oper1) {
                    case '+':
                        resTemp = num2 + num1;
                        break;
                    case '-':
                        resTemp = num2 - num1;
                        break;
                    case '*':
                        resTemp = num2 * num1;
                        break;
                    case '/':
                        resTemp = num2 / num1;
                        break;
                }
                nums.push(resTemp);
            } else {
                // 有俩元素 TODO
                Character oper2 = opers.pop();
            }
        }



        return nums.pop();
    }

    public static void main(String[] args) {
        System.out.println(plusAndMinus("123"));
        System.out.println(plusMinusMultiDivide("1/2"));
    }
}
