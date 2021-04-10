package com.ffl.study.java.tree.binarytree;

import lombok.Data;

/**
 * @author lff
 * @datetime 2020/12/25 20:45
 */
@Data
public class TreeNode<T extends Comparable<T>> {

    /**
     * 数据
     */
    private T data;

    /**
     * 左节点
     */
    private TreeNode<T> left;

    /**
     * 右节点
     */
    private TreeNode<T> right;

    public TreeNode(T data) {
        this.data = data;
    }

    /**
     * 前序遍历
     */
    public void preOrder() {
        System.out.println(this);

        if (this.getLeft() != null) {
            this.getLeft().preOrder();
        }

        if (this.getRight() != null) {
            this.getRight().preOrder();
        }
    }

    /**
     * 中序遍历
     */
    public void midOrder() {
        if (this.getLeft() != null) {
            this.getLeft().midOrder();
        }

        System.out.println(this);


        if (this.getRight() != null) {
            this.getRight().midOrder();
        }
    }

    /**
     * 后续遍历
     */
    public void postOrder() {
        if (this.getLeft() != null) {
            this.getLeft().postOrder();
        }

        if (this.getRight() != null) {
            this.getRight().postOrder();
        }

        System.out.println(this);
    }

    /**
     * 前序查找
     *
     * @param node
     * @return
     */
    public TreeNode<T> preOrderSearch(TreeNode<T> node) {
        System.out.println("进入前序遍历~~~");
        if (this.getData().compareTo(node.getData()) == 0) {
            return this;
        }

        TreeNode<T> resNode = null;
        if (this.getLeft() != null) {
            resNode = this.getLeft().preOrderSearch(node);
        }

        if (resNode != null) {
            return resNode;
        }

        if (this.getRight() != null) {
            resNode = this.getRight().preOrderSearch(node);
        }

        return resNode;
    }

    /**
     * 中序查找
     *
     * @param node
     * @return
     */
    public TreeNode<T> midOrderSearch(TreeNode<T> node) {
        TreeNode<T> resNode = null;
        if (this.getLeft() != null) {
            resNode = this.getLeft().midOrderSearch(node);
        }

        if (resNode != null) {
            return resNode;
        }

        System.out.println("进入中序遍历~~~");
        if (this.getData().compareTo(node.getData()) == 0) {
            return this;
        }

        if (this.getRight() != null) {
            resNode = this.getRight().midOrderSearch(node);
        }

        return resNode;
    }

    /**
     * 后序查找
     *
     * @param node
     * @return
     */
    public TreeNode<T> postOrderSearch(TreeNode<T> node) {
        TreeNode<T> resNode = null;

        if (this.getLeft() != null) {
            resNode = this.getLeft().postOrderSearch(node);
        }

        if (resNode != null) {
            return resNode;
        }

        System.out.println("进入后序遍历~~~");
        if (this.getRight() != null) {
            resNode = this.getRight().postOrderSearch(node);
        }

        if (resNode != null) {
            return resNode;
        }

        if (this.getData().compareTo(node.getData()) == 0) {
            return this;
        }
        return resNode;
    }

    /**
     * 递归删除节点
     * * 1.因为二叉树是单向的，所有应该判断当前节点的子节点是否需要删除，而不能判断当前节点是否需要删除
     * 2.如果当前节点的左子节点不为空，且左子节点就是要删除的节点，就将left置空，并且返回
     * 3.如果当前节点的右子节点不为空，且右子节点就是要删除的节点，就将right置空
     * 4.如果第2、3步没有删除，分别向左、右递归
     *
     * @param node
     */
    public void delete(TreeNode<T> node) {
        if (this.getLeft() != null || this.getLeft().getData().compareTo(node.getData()) == 0) {
            this.setLeft(null);
            return;
        }

        if (this.getRight() != null || this.getRight().getData().compareTo(node.getData()) == 0) {
            this.setRight(null);
            return;
        }

        if (this.getLeft() != null) {
            this.getLeft().delete(node);
        }

        if (this.getRight() != null) {
            this.getRight().delete(node);
        }
    }


    @Override
    public String toString() {
        return "TreeNode{" +
                "data=" + data +
                '}';
    }
}