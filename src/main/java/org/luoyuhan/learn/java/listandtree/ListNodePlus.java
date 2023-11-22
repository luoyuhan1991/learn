package org.luoyuhan.learn.java.listandtree;

import lombok.Data;
import org.luoyuhan.learn.common.model.ListNode;

import java.util.Stack;

/**
 * @author luoyuhan
 */
@Data
public class ListNodePlus {

    public static void main(String[] args) {
        ListNode plusNode1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))));
        ListNode plusNode2 = new ListNode(1, new ListNode(2, new ListNode(3)));
        ListNode plus = plus(plusNode1, plusNode2);
        ListNode.printListNode(plus);
        ListNode reverse = ReverseNode.reverse(plus);
        ListNode.printListNode(reverse);
        ListNode.printListNode(ReverseNode.reverseRecursive(reverse));
    }

    public static ListNode plus(ListNode list1, ListNode list2) {
        Stack<ListNode> stack1 = new Stack<>();
        Stack<ListNode> stack2 = new Stack<>();
        while (list1 != null) {
            stack1.push(list1);
            list1 = list1.next;
        }
        while (list2 != null) {
            stack2.push(list2);
            list2 = list2.next;
        }
        int sumUp = 0, sum;
        ListNode result = null;
        while (!stack1.isEmpty() && !stack2.isEmpty()) {
            sum = stack1.pop().val + stack2.pop().val + sumUp;
            sumUp = getSumUp(sum);
            result = insertHead(result, sum % 10);
        }
        while (!stack1.isEmpty()) {
            sum = stack1.pop().val + sumUp;
            sumUp = getSumUp(sum);
            result = insertHead(result, sum % 10);
        }
        while (!stack2.isEmpty()) {
            sum = stack2.pop().val + sumUp;
            sumUp = getSumUp(sum);
            result = insertHead(result, sum % 10);
        }
        if (sumUp == 1) {
            result = insertHead(result, 1);
        }
        return result;
    }

    private static int getSumUp(int sum) {
        int sumUp;
        if (sum > 9) {
            sumUp = 1;
        } else {
            sumUp = 0;
        }
        return sumUp;
    }

    private static ListNode insertHead(ListNode result, int i) {
        ListNode node = new ListNode(i);
        node.next = result;
        result = node;
        return result;
    }
}
