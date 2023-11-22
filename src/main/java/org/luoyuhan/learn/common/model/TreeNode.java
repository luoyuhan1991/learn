package org.luoyuhan.learn.common.model;

import java.util.List;

/**
 * @author luoyuhan
 */
public class TreeNode {
    public TreeNode parent = null;
    public int val;

    public List<TreeNode> getChild() {
        return null;
    }

    @Override
    public int hashCode() {
        return new Integer(val).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TreeNode)) {
            return false;
        }
        return val == ((TreeNode) obj).val;
    }
}
