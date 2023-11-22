package org.luoyuhan.learn.java.listandtree;

import org.luoyuhan.learn.model.ListNode;

public class FastSlowPointer {

    // 利用快慢指针查找链表中点
    public static ListNode middleNode(ListNode head) {
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    // 是否有环
    public static boolean listNodeHasCycle(ListNode head) {
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    // 查找环起点
    // 我们假设快慢指针相遇时，慢指针 slow 走了 k 步，那么快指针 fast 一定走了 2k 步：
    // fast 一定比 slow 多走了 k 步，这多走的 k 步其实就是 fast 指针在环里转圈圈，所以 k 的值就是环长度的「整数倍」。
    // 假设相遇点距环的起点的距离为 m，那么结合上图的 slow 指针，环的起点距头结点 head 的距离为 k - m，也就是说如果从 head 前进 k - m 步就能到达环起点。
    // 巧的是，如果从相遇点继续前进 k - m 步，也恰好到达环起点。因为结合上图的 fast 指针，从相遇点开始走k步可以转回到相遇点，那走 k - m 步肯定就走到环起点了：
    // 所以，只要我们把快慢指针中的任一个重新指向 head，然后两个指针同速前进，k - m 步后一定会相遇，相遇之处就是环的起点了。
    public static ListNode findCycleStart(ListNode head) {
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                break;
            }
        }
        if (fast == null || fast.next == null) {
            // 无环
            return null;
        }
        slow = head;
        while (slow != fast) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    // 两个链表的交点 a1 a2 c1 c2, b1 b2 b3 c1 c2
    // a1 a2 c1 c2 b1 b2 b3 c1 c2 4 + 5 = 9
    // b1 b2 b3 c1 c2 a1 a2 c1 c2 5 + 4 = 9
    // 把两个链表交替连起来, 形成的两个新链表长度一致, 往前c个看两链表元素, 相等就是交点, 不相等就没有交点
    // 做法就是两个指针遍历, 两指针相等则停止, 停止条件有两个, 相等不为空即为交点, 都到尾巴(即都为空)没有交点
    ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p1 = headA, p2 = headB;
        // 最多走9步，第10步两个都是null
        while (p1 != p2) {
            // A走完了走B
            if (p1 == null) {
                p1 = headB;
            } else {
                p1 = p1.next;
            }
            // B走完了走A
            if (p2 == null) {
                p2 = headA;
            } else {
                p2 = p2.next;
            }
        }
        // 找到了就是p1, 没找到p1也是空
        return p1;
    }
}
