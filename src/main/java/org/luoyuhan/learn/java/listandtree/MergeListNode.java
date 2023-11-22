package org.luoyuhan.learn.java.listandtree;

import org.luoyuhan.learn.common.model.ListNode;
import org.luoyuhan.learn.common.model.NodeUtil;
import org.luoyuhan.learn.java.serialiazation.ProtoStuffUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MergeListNode {
    public static ListNode mergeTwoList(ListNode listNode1, ListNode listNode2) {
        ListNode dummy = new ListNode();
        ListNode head = dummy;
        while (listNode1 != null && listNode2 != null) {
            if (listNode1.val <= listNode2.val) {
                head.next = listNode1;
                listNode1 = listNode1.next;
            } else {
                head.next = listNode2;
                listNode2 = listNode2.next;
            }
            head = head.next;
        }
        if (listNode1 != null) {
            head.next = listNode1;
        }
        if (listNode2 != null) {
            head.next = listNode2;
        }
        return dummy.next;
    }

    // 利用堆排序辅助合并多个链表，从堆中取出多个链表头结点的最值，被取值的链表头结点后移
    public static ListNode mergeListNodes(List<ListNode> listNodes) {
        ListNode dummy = new ListNode();
        ListNode head = dummy;

        PriorityQueue<ListNode> queue = new PriorityQueue<>(listNodes.size(), (Comparator.comparingInt(o -> o.val)));
        for (ListNode listNode : listNodes) {
            if (listNode != null) {
                queue.offer(listNode);
            }
        }

        while (!queue.isEmpty()) {
            ListNode poll = queue.poll();
            head.next = poll;
            if (poll.next != null) {
                queue.offer(poll.next);
            }
            head = head.next;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode listNode1 = ProtoStuffUtil.deserializer(ProtoStuffUtil.serializer(NodeUtil.listNode1), ListNode.class);
        ListNode listNode2 = ProtoStuffUtil.deserializer(ProtoStuffUtil.serializer(NodeUtil.listNode2), ListNode.class);
        ListNode listNode3 = ProtoStuffUtil.deserializer(ProtoStuffUtil.serializer(NodeUtil.listNode3), ListNode.class);
        ListNode.printListNode(listNode1);
        ListNode.printListNode(listNode2);
        ListNode.printListNode(listNode3);
        System.out.println();
        ListNode.printListNode(mergeTwoList(NodeUtil.listNode1, NodeUtil.listNode2));
        ListNode.printListNode(mergeListNodes(Arrays.asList(listNode1, listNode2, listNode3)));
    }
}
