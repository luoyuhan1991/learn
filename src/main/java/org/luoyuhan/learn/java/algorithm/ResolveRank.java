package org.luoyuhan.learn.java.algorithm;

import com.google.common.collect.Collections2;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ResolveRank {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("A", "B", "C", "D", "E");
        Collection<List<String>> permutations = Collections2.permutations(strings);
        permutations.forEach(list -> {
            String two = list.get(1);
            String three = list.get(2);
            if ("E".equals(two) || "E".equals(three)) {
                return;
            }
            String one = list.get(0);
            String four = list.get(3);
            String five = list.get(4);
            try {
                if ((Boolean) ResolveRank.class.getMethod(one + "Said", List.class).invoke(ResolveRank.class, list) &&
                        (Boolean) ResolveRank.class.getMethod(two + "Said", List.class).invoke(ResolveRank.class, list) &&
                        !((Boolean) ResolveRank.class.getMethod(three + "Said", List.class).invoke(ResolveRank.class, list)) &&
                        !((Boolean) ResolveRank.class.getMethod(four + "Said", List.class).invoke(ResolveRank.class, list)) &&
                        !((Boolean) ResolveRank.class.getMethod(five + "Said", List.class).invoke(ResolveRank.class, list))
                ) {
                    // 找出答案
                    System.out.println(list);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static boolean ASaid(List<String> strings) {
        return "E".equals(strings.get(0));
    }

    public static boolean BSaid(List<String> strings) {
        return "B".equals(strings.get(1));
    }

    public static boolean CSaid(List<String> strings) {
        return "A".equals(strings.get(strings.size() - 1));
    }

    public static boolean DSaid(List<String> strings) {
        return !"C".equals(strings.get(0));
    }

    public static boolean ESaid(List<String> strings) {
        return "D".equals(strings.get(0));
    }
}
