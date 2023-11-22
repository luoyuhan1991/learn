package org.luoyuhan.learn.java.bfsdfs;

import org.luoyuhan.learn.common.model.NodeUtil;
import org.luoyuhan.learn.common.model.TreeNode;

import java.util.*;

public class BFSDFS {
    public static int bfsFramework(TreeNode start, TreeNode target) {
        Queue<TreeNode> queue = new LinkedList<>();// 核心数据结构
        Set<TreeNode> visited = new HashSet<>();// 避免走回头路

        queue.offer(start);// 起点加入队列
        visited.add(start);
        int step = 0;// 记录扩散的步数

        while (!queue.isEmpty()) {
            int size = queue.size();
            // 将当前队列中的所有节点向四周扩散
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                // 划重点 这里判断是否到达终点
                if (cur.val == target.val) {
                    return step;
                }
                // 将cur的相邻节点加入队列
                for (TreeNode x : cur.getChild()) {
                    if (!visited.contains(x)) {
                        queue.offer(x);
                        visited.add(x);
                    }
                }
            }
            // 划重点 更新步数
            step++;
        }
        return step;
    }

    public static int dfsFramework(TreeNode start, TreeNode target) {
        Stack<TreeNode> stack = new Stack<>();
        Set<TreeNode> visited = new HashSet<>();

        stack.push(start);
        visited.add(start);
        int step = 0;

        while (!stack.isEmpty()) {
            int size = stack.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = stack.pop();
                if (cur.val == target.val) {
                    return step;
                }
                for (TreeNode x : cur.getChild()) {
                    if (!visited.contains(x)) {
                        stack.push(x);
                        visited.add(x);
                    }
                }
            }
            step++;
        }
        return step;
    }

    //        1                     1
    //     2    3             2     3     4
    //   4  5  6  7        5     6     7     8
    // 8                9     10 11  12 13     14
    public static void main(String[] args) {
        TreeNode binaryTree = NodeUtil.binaryTree;
        TreeNode tripleTree = NodeUtil.tripleTree;
        System.out.println("  step:" + bfsMinPath(binaryTree, 1));
        System.out.println("  step:" + bfsMinPath(binaryTree, 3));
        System.out.println("  step:" + bfsMinPath(binaryTree, 5));
        System.out.println("  step:" + bfsMinPath(binaryTree, 8));
        System.out.println("  step:" + bfsMinPath(tripleTree, 1));
        System.out.println("  step:" + bfsMinPath(tripleTree, 3));
        System.out.println("  step:" + bfsMinPath(tripleTree, 7));
        System.out.println("  step:" + bfsMinPath(tripleTree, 11));

        System.out.println("  step:" + dfsFindPathButNotMin(binaryTree, 1));
        System.out.println("  step:" + dfsFindPathButNotMin(binaryTree, 3));
        System.out.println("  step:" + dfsFindPathButNotMin(binaryTree, 5));
        System.out.println("  step:" + dfsFindPathButNotMin(binaryTree, 8));
        System.out.println("  step:" + dfsFindPathButNotMin(tripleTree, 1));
        System.out.println("  step:" + dfsFindPathButNotMin(tripleTree, 3));
        System.out.println("  step:" + dfsFindPathButNotMin(tripleTree, 7));
        System.out.println("  step:" + dfsFindPathButNotMin(tripleTree, 11));
    }

    public static int bfsMinPath(TreeNode start, int end) {
        Queue<TreeNode> queue = new LinkedList<>();
        HashSet<Integer> visited = new HashSet<>();

        queue.offer(start);
        visited.add(start.val);
        int step = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                if (cur.val == end) {
                    System.out.print("path:");
                    print(cur);
                    return step;
                }
                for (TreeNode x : cur.getChild()) {
                    if (!visited.contains(x.val)) {
                        x.parent = cur;
                        queue.offer(x);
                        visited.add(x.val);
                    }
                }
            }
            step++;
        }
        return step;
    }

    public static int dfsFindPathButNotMin(TreeNode start, int target) {
        Stack<TreeNode> stack = new Stack<>();
        Set<TreeNode> visited = new HashSet<>();

        stack.push(start);
        visited.add(start);
        int step = 0;

        while (!stack.isEmpty()) {
            int size = stack.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = stack.pop();
                if (cur.val == target) {
                    System.out.print("path:");
                    print(cur);
                    return step;
                }
                for (TreeNode x : cur.getChild()) {
                    if (!visited.contains(x)) {
                        x.parent = cur;
                        stack.push(x);
                        visited.add(x);
                    }
                }
            }
            step++;
        }
        return step;
    }

    public static void print(TreeNode node) {
        if (node.parent != null) {
            print(node.parent);
        }
        System.out.print(node.val);
    }
}
