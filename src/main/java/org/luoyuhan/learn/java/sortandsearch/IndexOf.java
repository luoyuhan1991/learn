package org.luoyuhan.learn.java.sortandsearch;

public class IndexOf {
    public static void main(String[] args) {
        //Test 1
        String p1 = "abcdef", c1 = "cde";
        System.out.println(sysIndexOf(p1, c1) == myIndexOf(p1, c1));

        //Test 2
        String p2 = "abcdef", c2 = "cdf";
        System.out.println(sysIndexOf(p2, c2) == myIndexOf(p2, c2));

        //Test 3
        String p3 = "abcdef", c3 = "efg";
        System.out.println(sysIndexOf(p3, c3) == myIndexOf(p3, c3));

        //Test 4
        String p4 = "abcdef", c4 = "aaaaaaa";
        System.out.println(sysIndexOf(p4, c4) == myIndexOf(p4, c4));

        //Test 5
        String p5 = "aaabcde", c5 = "aab";
        System.out.println(sysIndexOf(p5, c5) == myIndexOf(p5, c5));
    }

    /**
     * 系统String类内置的indexOf实现
     */
    public static int sysIndexOf(String p, String c) {
        return p.indexOf(c);
    }

    /**
     * 需要实现相同功能的函数，使用for循环遍历pChars和cChars的方法
     */
    public static int myIndexOf(String p, String c) {
        char[] pChars = p.toCharArray();
        char[] cChars = c.toCharArray();
        //TODO 需要在此处实现代码
        for (int i = 0; i < pChars.length; i++) {
            int iTemp = i;
            for (int j = 0; j < cChars.length && iTemp < pChars.length; j++, iTemp++) {
                if (pChars[iTemp] != cChars[j]) {
                    break;
                }
                if (j == cChars.length - 1) {
                    return i;
                }
            }
        }
        return -1;
    }
}
