package org.luoyuhan.learn.java.listandtree;

import org.luoyuhan.learn.model.ListNode;

/**
 * @author luoyuhan
 */
public class ReverseNode {
    // pre -> cur -> next ->  1
    // pre <- cur    next ->  2
    // nul <- pre    cur  ->  3,4
    public static ListNode reverse(ListNode node) {
        ListNode pre = null;
        ListNode cur = node;
        ListNode next;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    // 截取法
    public static ListNode reverseNodeCutMN(ListNode head, int m, int n) {
        ListNode headPre = new ListNode(-1);
        headPre.next = head;
        ListNode pre = headPre;
        ListNode cur = head;
        // 记录开头
        for (int i = 0; i < m - 1; i++) {
            pre = pre.next;
            cur = cur.next;
        }
        // 记录结尾
        ListNode nNode = cur;
        for (int i = m; i < n; i++) {
            nNode = nNode.next;
        }
        // 断开链表
        pre.next = null;
        ListNode nNext = nNode.next;
        nNode.next = null;
        // 翻转
        reverse(cur);
        // 拼回
        pre.next = nNode;
        cur.next = nNext;
        return headPre.next;
    }

    // 头插法
    public static ListNode reverseNodeMN(ListNode head, int m, int n) {
        ListNode headPre = new ListNode(-1);
        headPre.next = head;
        ListNode pre = headPre;
        ListNode cur = head;
        // 找到M
        for (int i = 1; i < m; i++) {
            pre = cur;
            cur = cur.next;
        }
        // 从M到N翻转，将每个后续的节点都放到M的位置就能完成翻转，类似于洗牌
        // -112345 -121345 -132145 -143215 -154321
        for (int i = m; i < n; i++) {
            ListNode next = cur.next; // next = 2
            cur.next = next.next; // 1 -> 3
            next.next = pre.next; // 2 -> 1
            pre.next = next; // -1 -> 2
        }
        return headPre.next;
    }

    // node2 => reverse(=>2 head =>1 next => null)
    // https://zhuanlan.zhihu.com/p/86745433
    public static ListNode reverseRecursive(ListNode head) {
        if (head.next == null) {
            return head;
        }
        // reverse 这里已经反转完成，考虑后续怎么处理
        ListNode plusNode = reverseRecursive(head.next);
        // 1 走到底了返回了 在这里转向
        head.next.next = head;
        // 2 转完向head已是尾节点
        head.next = null;
        return plusNode;
    }

    // 反转前N个节点
    // 1 -> 2 -> 3 -> 4 -> 5
    // 1 <- 2 <- 3    4 -> 5
    public static ListNode successor = null;
    public static ListNode reverseRecursiveN(ListNode head, int n) {
        if (n == 1) {
            // 4 记录原链表的第一个不被转向的节点
            successor = head.next;
            return head;
        }
        ListNode last = reverseRecursiveN(head.next, n - 1);
        head.next.next = head;
        // 尾节点连到原链表上去
        head.next = successor;
        return last;
    }

    // 反转m,n节点，利用反转前N，一次后移，找到m为1的位置： * * m n * * => * * m=1 m-n * *
    public static ListNode reverseRecursiveMN(ListNode head, int m, int n) {
        if (m == 1) {
            return reverseRecursiveN(head, n);
        }
        // 后移
        head.next = reverseRecursiveMN(head.next, m - 1, n - 1);
        return head;
    }

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        listNode = reverseNodeCutMN(listNode, 2, 4);
        ListNode.printListNode(listNode);
        listNode = reverseNodeMN(listNode, 1, 5);
        ListNode.printListNode(listNode);
        listNode = reverseNodeMN(listNode, 1, 5);
        ListNode.printListNode(listNode);
        listNode = reverseRecursiveN(listNode, 3);
        ListNode.printListNode(listNode);
        listNode = reverseRecursiveMN(listNode, 1, 3);
        ListNode.printListNode(listNode);
        listNode = reverseRecursiveMN(listNode, 2, 3);
        ListNode.printListNode(listNode);
    }
}
