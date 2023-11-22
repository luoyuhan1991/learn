package org.luoyuhan.learn.common.model;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luoyuhan
 */
@AllArgsConstructor
public class TripleTree extends TreeNode {
    public TripleTree left = null;
    public TripleTree mid = null;
    public TripleTree right = null;

    public TripleTree(int val) {
        this.val = val;
    }

    public TripleTree(int val, TripleTree left, TripleTree mid, TripleTree right) {
        this.val = val;
        this.left = left;
        this.mid = mid;
        this.right = right;
    }

    @Override
    public List<TreeNode> getChild() {
        List<TreeNode> children = new ArrayList<>();
        if (left != null) {
            children.add(left);
        }
        if (mid != null) {
            children.add(mid);
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
