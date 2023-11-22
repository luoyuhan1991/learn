package org.luoyuhan.learn.java.listandtree;

import org.luoyuhan.learn.common.model.ListNode;

import java.util.HashMap;
import java.util.Map;

public class DeleteDuplicateNode {
    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(3
                , new ListNode(3, new ListNode(4, new ListNode(4, new ListNode(5))))))));

        pointDelete(head);

        ListNode.printListNode(head);


        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(3
                , new ListNode(3, new ListNode(4, new ListNode(4, new ListNode(5))))))));

        hashDelete(head);

        ListNode.printListNode(head);
    }

    public static void pointDelete(ListNode head) {
        if (head == null) return;
        ListNode pre = new ListNode();
        pre.next = head;
        ListNode cur = head;
        ListNode next = cur.next;
        while (cur != null) {
            // 找出相同的
            while (next != null && cur.val != next.val) {
                pre = cur;
                cur = next;
                next = cur.next;
            }
            // 找出不同的
            while (next != null && cur.val == next.val) {
                cur = next;
                next = cur.next;
            }
            // 删除重复的
            if (next != null) {
                pre.next = next;
            }

            // 后移
            cur = next;
            if (cur != null)
                next = cur.next;
        }
    }

    // 12333445
    public static void hashDelete(ListNode head) {
        Map<Integer, Integer> map = new HashMap<>();
        ListNode cur = head;
        while (cur != null) {
            map.put(cur.val, map.getOrDefault(cur.val, 0) + 1);
            cur = cur.next;
        }
        cur = head;
        ListNode pre = new ListNode();
        pre.next = cur;
        while (cur != null) {
            if (map.get(cur.val) > 1) {
                pre.next = cur.next;
            } else if (map.get(cur.val) == 1) {
                pre = cur;
            }
            cur = cur.next;
        }
    }
}
