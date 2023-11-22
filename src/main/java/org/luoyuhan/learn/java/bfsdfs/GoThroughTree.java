package org.luoyuhan.learn.java.bfsdfs;

import org.luoyuhan.learn.common.model.BinaryTree;
import org.luoyuhan.learn.common.model.TripleTree;

import java.util.*;

/**
 * @author luoyuhan
 */
public class GoThroughTree {
    public static void levelThrough(BinaryTree p) {
        System.out.println("层序遍历(队列)");
        if (p == null) {
            return;
        }
        Queue<BinaryTree> queue = new LinkedList<>();
        queue.offer(p);
        while (!queue.isEmpty()) {
            BinaryTree t = queue.poll();
            System.out.print(t.val);
            if (t.left != null) {
                queue.offer(t.left);
            }
            if (t.right != null) {
                queue.offer(t.right);
            }
        }
        System.out.println();
    }

    public static void frontThroughRecursive(BinaryTree node) {
        if (node == null) {
            return;
        }
        System.out.print(node.val);
        if (node.left != null) {
            frontThroughRecursive(node.left);
        }
        if (node.right != null) {
            frontThroughRecursive(node.right);
        }
    }

    public static void middleThroughRecursive(BinaryTree node) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            middleThroughRecursive(node.left);
        }
        System.out.print(node.val);
        if (node.right != null) {
            middleThroughRecursive(node.right);
        }
    }

    public static void endThroughRecursive(BinaryTree node) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            endThroughRecursive(node.left);
        }
        if (node.right != null) {
            endThroughRecursive(node.right);
        }
        System.out.print(node.val);
    }

    // https://blog.csdn.net/Benja_K/article/details/88389039 前中后序遍历非递归，栈，入栈时访问-前，出栈是访问-中
    // 后序：记录次数，入栈为1 不直接出栈，先peek，1时变2取右，2时出栈再访问即是后续
    public static void frontThroughStack(BinaryTree node) {
        System.out.println("前序遍历非递归(栈)");
        Stack<BinaryTree> stack = new Stack<>();
        BinaryTree p = node;
        while (!stack.isEmpty() || p != null) {
            if (p != null) {
                stack.push(p);
                System.out.print(p.val);
                p = p.left;
            } else {
                p = stack.pop();
                p = p.right;
            }
        }
        System.out.println();
    }

    public static void middleThroughStack(BinaryTree node) {
        System.out.println("中序遍历非递归(栈)");
        Stack<BinaryTree> stack = new Stack<>();
        BinaryTree p = node;
        while (!stack.isEmpty() || p != null) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                p = stack.pop();
                System.out.print(p.val);
                p = p.right;
            }
        }
        System.out.println();
    }

    public static void endThroughStack(BinaryTree node) {
        System.out.println("后序遍历非递归(栈)");
        Stack<BinaryTree> stack = new Stack<>();
        BinaryTree p = node;
        // 用map记录访问次数
        Map<BinaryTree, Integer> nodeMap = new HashMap<>();
        while (!stack.isEmpty() || p != null) {
            if (p != null) {
                stack.push(p);
                nodeMap.put(p, 1);
                p = p.left;
            } else {
                p = stack.peek();
                if (nodeMap.get(p) == 1) {
                    nodeMap.put(p, 2);
                    p = p.right;
                } else {
                    p = stack.pop();
                    System.out.print(p.val);
                    p = null;
                }
            }
        }
        System.out.println();
    }

    public static void deepFirstSearch(TripleTree head) {
        System.out.println("深度优先搜索(栈,前序遍历)");
        if (head == null) {
            return;
        }
        Stack<TripleTree> stack = new Stack<>();
        stack.push(head);
        while (!stack.isEmpty()) {
            TripleTree node = stack.pop();
            System.out.print(node.val);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.mid != null) {
                stack.push(node.mid);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
    }

    public static void breadthFirstSearch(TripleTree head) {
        System.out.println("广度优先搜索(队列,层序遍历)");
        if (head == null) {
            return;
        }
        Queue<TripleTree> queue = new LinkedList<>();
        queue.offer(head);
        while (!queue.isEmpty()) {
            TripleTree node = queue.poll();
            System.out.print(node.val);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.mid != null) {
                queue.offer(node.mid);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    public static void bfs(TripleTree root) {
        Deque<TripleTree> deque = new LinkedList<>();
        deque.offer(root);
        List<Integer> ans = new ArrayList<>();
        while (!deque.isEmpty()) {
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                TripleTree poll = deque.poll();
                ans.add(poll.val);
                if (poll.left != null) {
                    deque.offer(poll.left);
                }
                if (poll.mid != null) {
                    deque.offer(poll.mid);
                }
                if (poll.right != null) {
                    deque.offer(poll.right);
                }
            }
        }
        System.out.println(ans);
    }

    public static void bfs1(TripleTree root) {
        Deque<TripleTree> deque = new LinkedList<>();
        deque.offer(root);
        List<Integer> ans = new ArrayList<>();
        while (!deque.isEmpty()) {
            TripleTree poll = deque.poll();
            ans.add(poll.val);
            if (poll.left != null) {
                deque.offer(poll.left);
            }
            if (poll.mid != null) {
                deque.offer(poll.mid);
            }
            if (poll.right != null) {
                deque.offer(poll.right);
            }
        }
        System.out.println(ans);
    }

    public static void dfs(TripleTree root) {
        Deque<TripleTree> deque = new LinkedList<>();
        deque.push(root);
        List<Integer> ans = new ArrayList<>();
        while (!deque.isEmpty()) {
            TripleTree pop = deque.pop();
            ans.add(pop.val);
            if (pop.left != null) {
                deque.push(pop.left);
            }
            if (pop.mid != null) {
                deque.push(pop.mid);
            }
            if (pop.right != null) {
                deque.push(pop.right);
            }
        }
        System.out.println(ans);
    }

    //        1                     1
    //     2    3             2     3     4
    //   4  5  6  7        5     6     7     8
    // 8                9     10 11  12 13     14
    public static void main(String[] args) {
        BinaryTree node = new BinaryTree(1, new BinaryTree(2, new BinaryTree(4, new BinaryTree(8), null), new BinaryTree(5)), new BinaryTree(3, new BinaryTree(6), new BinaryTree(7)));
        levelThrough(node);
        System.out.println("前序遍历递归");
        frontThroughRecursive(node);
        System.out.println();
        frontThroughStack(node);
        System.out.println("中序遍历递归");
        middleThroughRecursive(node);
        System.out.println();
        middleThroughStack(node);
        System.out.println("后序遍历递归");
        endThroughRecursive(node);
        System.out.println();
        endThroughStack(node);
        TripleTree tripleTree = new TripleTree(1,
                new TripleTree(2, new TripleTree(5, new TripleTree(9), null, null), null, null),
                new TripleTree(3, new TripleTree(6, new TripleTree(10), null, new TripleTree(11)), null, new TripleTree(7, new TripleTree(12), null, new TripleTree(13))),
                new TripleTree(4, null, null, new TripleTree(8, null, null, new TripleTree(14))));
        // 深度 栈 前序遍历
        deepFirstSearch(tripleTree);
        System.out.println();
        // 广度 队列 层序遍历
        breadthFirstSearch(tripleTree);
        System.out.println();
        bfs(tripleTree);
        bfs1(tripleTree);
        dfs(tripleTree);
    }
}
