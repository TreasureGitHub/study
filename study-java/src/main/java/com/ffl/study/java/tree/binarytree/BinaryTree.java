package com.ffl.study.java.tree.binarytree;

import lombok.Data;

/**
 * @author lff
 * @datetime 2020/06/24 00:55
 */
@Data
public class BinaryTree<T extends Comparable<T>> {

    /**
     * 根节点
     */
    private TreeNode<T> root;

    /**
     * 前序
     */
    public void preOrder() {
        if (this.root != null) {
            this.root.preOrder();
        }
    }

    /**
     * 中序
     */
    public void midOrder() {
        if (this.root != null) {
            this.root.midOrder();
        }
    }

    /**
     * 后序
     */
    public void postOrder() {
        if (this.root != null) {
            this.root.postOrder();
        }
    }

    /**
     * 前序遍历
     *
     * @param data
     * @return
     */
    public TreeNode<T> preOrderSearch(TreeNode<T> data) {
        if (root == null) {
            return null;
        }

        return root.preOrderSearch(data);
    }

    /**
     * 中序遍历
     *
     * @param data
     * @return
     */
    public TreeNode midOrderSearch(TreeNode data) {
        if (root == null) {
            return null;
        }

        return root.midOrderSearch(data);
    }

    /**
     * 后序遍历
     *
     * @param data
     * @return
     */
    public TreeNode postOrderSearch(TreeNode data) {
        if (root == null) {
            return null;
        }

        return root.postOrderSearch(data);
    }

    public void delete(TreeNode<T> node) {
        if (root == null) {
            return;
        }

        if (root.getData().compareTo(node.getData()) == 0) {
            root = null;
        } else {
            root.delete(node);
        }
    }
}
