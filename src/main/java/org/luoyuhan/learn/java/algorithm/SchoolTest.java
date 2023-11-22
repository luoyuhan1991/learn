package org.luoyuhan.learn.java.algorithm;

import org.luoyuhan.common.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SchoolTest {

    static Map<Character, Pair<Character, int[]>> map = new HashMap<>();

    public static Character nomals[] = new Character[5];

    public SchoolTest() {
        map.put('A', new Pair<>('E', new int[]{1}));
        map.put('B', new Pair<>('B', new int[]{2}));
        map.put('C', new Pair<>('A', new int[]{5}));
        map.put('D', new Pair<>('C', new int[]{2, 3, 4, 5}));
        map.put('E', new Pair<>('D', new int[]{1}));
    }

    public static void main(String[] args) {
        new SchoolTest();
        for (int i = 1; i <= map.size(); i++) {
            nomals[0] = (char) ('A' + i - 1);
            digui(1);
        }
    }

    public static void digui(int one) {
        if (nomals[0] != null && nomals[1] != null && one != 1) {
            //由于进到这里的方法都是，排位第三到五的顺位，所以认为都是虚假言论
            for (int j = 1; j <= map.size(); j++) {
                char num = (char) ('A' + j - 1);
                boolean flag = true;
                //这里的作用，就是判断数组里是否已经有这个字母了。k<=one：只判断已经填充的字母
                for (int k = 1; k <= map.size(); k++) {
                    if (k <= one && nomals[k - 1] != null && nomals[k - 1] == num) {
                        flag = false;
                        break;
                    }
                }
                if (!flag) {
                    continue;
                }

                if (judge(one + 1, num)) {
                    nomals[one] = num;
                    if (one == 4) {
                        System.out.println(Arrays.toString(nomals));
                    } else {
                        digui(one + 1);
                    }
                }
            }
        } else {
            for (int j = 1; j <= map.size(); j++) {
                if (j == (nomals[0] - 64)) {
                    continue;
                }
                nomals[1] = (char) ('A' + j - 1);
                if (judge(one + 1, nomals[1])) {
                    digui(one + 1);
                }
            }
        }
    }

    /**
     * @param round 第几轮
     * @param num   哪个字母
     * @return
     */
    public static boolean judge(int round, char num) {
        //代表说的是真的

        Pair<Character, int[]> one = map.get(nomals[0]);
        Pair<Character, int[]> two = map.get(nomals[1]);

        //第一名所说的,和传入的这个字母自己所在的排名不符合
        if (one.getKey() == num && Arrays.binarySearch(one.getValue(), round) < 0) {
            return false;
            //第二名所说的，和传入的这个字母自己所在的排名不符合
        } else if (two.getKey() == num && Arrays.binarySearch(two.getValue(), round) < 0) {
            return false;
        }
        //判断第一名是否为真
        if (one.getKey() == nomals[0] && Arrays.binarySearch(one.getValue(), 1) < 0) {
            return false;
        }
        if (two.getKey() == nomals[0] && Arrays.binarySearch(two.getValue(), 1) < 0) {
            return false;
        }


        //以上都是说真话的判断，现在是说假话的判断
        for (Map.Entry<Character, Pair<Character, int[]>> entry : map.entrySet()) {
            Pair<Character, int[]> fake = entry.getValue();
            if (fake == one || fake == two) {
                continue;
            } else {
                //先判断，第一第二名的真伪
                if (fake.getKey() == nomals[0] && Arrays.binarySearch(one.getValue(), 1) >= 0) {
                    return false;
                } else if (fake.getKey() == nomals[1] && Arrays.binarySearch(one.getValue(), 2) >= 0) {
                    return false;
                    //再判断 传入的这个字母自己所在的排名，和这个说谎话的人所说的是否一致，一致就说明是不正确的排名
                } else if (fake.getKey() == num && Arrays.binarySearch(fake.getValue(), round) >= 0) {
                    return false;
                }
            }
        }
        return true;


    }
}

