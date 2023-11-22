package org.luoyuhan.learn.confirm;

public class Test {
    public static void main(String[] args) {
        String str = "abc" + 1;
        String a = str + 2;

        String st1 = "abc";
        System.out.println(st1 == "abc");
        String st2 = new String("abc");
        System.out.println(st2 == "abc");
    }
}
