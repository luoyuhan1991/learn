package org.luoyuhan.learn.common.model;

public class NodeUtil {
    //        1                     1
    //     2    3             2     3     4
    //   4  5  6  7        5     6     7     8
    // 8                9     10 11  12 13     14

    public static BinaryTree binaryTree =
            new BinaryTree(1,
                    new BinaryTree(2,
                            new BinaryTree(4,
                                    new BinaryTree(8), null),
                            new BinaryTree(5)),
                    new BinaryTree(3,
                            new BinaryTree(6),
                            new BinaryTree(7)));
    public static TripleTree tripleTree =
            new TripleTree(1,
                new TripleTree(2,
                        new TripleTree(5,
                                new TripleTree(9), null, null), null, null),
                new TripleTree(3,
                        new TripleTree(6,
                                new TripleTree(10), null,
                                new TripleTree(11)), null,
                        new TripleTree(7,
                                new TripleTree(12), null,
                                new TripleTree(13))),
                new TripleTree(4, null, null,
                        new TripleTree(8, null, null,
                                new TripleTree(14))));

    public static ListNode listNode1 = new ListNode(1, new ListNode(4));
    public static ListNode listNode2 = new ListNode(3, new ListNode(6, new ListNode(8, new ListNode(10))));
    public static ListNode listNode3 = new ListNode(2, new ListNode(5, new ListNode(6, new ListNode(9))));
}
