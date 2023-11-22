package org.luoyuhan.learn.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luoyuhan
 */
public class BinaryTree extends TreeNode {
    public BinaryTree left = null;
    public BinaryTree right = null;

    public BinaryTree(int val) {
        this.val = val;
    }

    public BinaryTree(int val, BinaryTree left, BinaryTree right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public List<TreeNode> getChild() {
        List<TreeNode> children = new ArrayList<>();
        if (left != null) {
            children.add(left);
        }
        if (right != null) {
            children.add(right);
        }
        return children;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
