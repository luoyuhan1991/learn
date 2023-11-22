package org.luoyuhan.learn.confirm;

public class Confirm {
    public static void main(String[] args) throws InterruptedException {
        Integer x = new Integer(18);
        Integer y = new Integer(18);
        System.out.println(x == y); // false

        Integer a = Integer.valueOf(18);
        Integer b = Integer.valueOf(18);
        System.out.println(a == b); // true

        Integer m = Integer.valueOf(300);
        Integer n = Integer.valueOf(300);
        System.out.println(m == n); // false

        Integer p = new Integer(10);
        Integer q = Integer.valueOf(10);
        System.out.println(p == q); // false

        StringBuilder s1 = new StringBuilder("one");
        StringBuffer s2 = new StringBuffer("one");
        s1.append("1");
        s2.append("1");
        System.out.println(s1.toString().equals(s2.toString()));
    }
}
