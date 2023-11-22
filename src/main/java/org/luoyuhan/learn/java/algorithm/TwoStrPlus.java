package org.luoyuhan.learn.java.algorithm;

public class TwoStrPlus {
    public static String plus(String a, String b) {
        int aL = a.length();
        int bL = b.length();
        if (aL == 0 && bL == 0) {
            return "";
        }
        if (aL == 0) {
            return b;
        }
        if (bL == 0) {
            return a;
        }
        StringBuffer result = new StringBuffer();
        // 进位
        int mark = 0;
        do {
            int tempSum = Integer.parseInt(a.charAt(aL - 1) + "") + Integer.parseInt(b.charAt(bL - 1) + "") + mark;
            if (tempSum >= 10) {
                mark = 1;
            } else {
                mark = 0;
            }
            result.append(tempSum % 10);
            aL--;
            bL--;
        } while (aL > 0 && bL > 0);
        // 处理剩余
        if (aL > 0) {
            do {
                int tempSum = Integer.parseInt(a.charAt(aL - 1) + "") + mark;
                if (tempSum >= 10) {
                    mark = 1;
                } else {
                    mark = 0;
                }
                result.append(tempSum % 10);
                aL--;
            } while (aL > 0);
        }
        if (bL > 0) {
            do {
                int tempSum = Integer.parseInt(b.charAt(bL - 1) + "") + mark;
                if (tempSum >= 10) {
                    mark = 1;
                } else {
                    mark = 0;
                }
                result.append(tempSum % 10);
                bL--;
            } while (bL > 0);
        }
        if (mark > 0) {
            result.append("1");
        }
        return result.reverse().toString();
    }
}
