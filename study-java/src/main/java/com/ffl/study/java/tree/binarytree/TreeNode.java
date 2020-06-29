package com.ffl.study.java.tree.binarytree;

import com.ffl.study.common.constants.StringConstants;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lff
 * @datetime 2020/06/24 01:31
 * <p>
 * 节点信息
 */
public class TreeNode<T extends Comparable<T>> {

    /**
     * 数据
     */
    @Getter
    @Setter
    private T data;

    /**
     * 左节点
     */
    @Getter
    @Setter
    private TreeNode<T> left;

    /**
     * 右节点
     */
    @Getter
    @Setter
    private TreeNode<T> right;

    public TreeNode(T data) {
        this.data = data;
    }

    /**
     * 前序遍历
     */
    public void preOrder() {

        if (data == null) {
            return;
        }

        System.out.print(data + StringConstants.TAB);

        if (getLeft() != null) {
            getLeft().preOrder();
        }

        if (getRight() != null) {
            getRight().preOrder();
        }
    }

    /**
     * 中序遍历
     */
    public void midOrder() {

        if (data == null) {
            return;
        }

        if (getLeft() != null) {
            getLeft().midOrder();
        }

        System.out.print(data + StringConstants.TAB);


        if (getRight() != null) {
            getRight().midOrder();
        }
    }

    /**
     * 后序遍历
     */
    public void postOrder() {

        if (data == null) {
            return;
        }

        if (getLeft() != null) {
            getLeft().postOrder();
        }

        if (getRight() != null) {
            getRight().postOrder();
        }

        System.out.print(data + StringConstants.TAB);

    }

    /**
     * 前序查找
     *
     * @param node
     * @return
     */
    public TreeNode<T> preOrderSearch(TreeNode<T> node) {
        System.out.println("进入前序查找！");
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

        System.out.println("进入中序查找！");
        if (this.getData().compareTo(node.getData()) == 0) {
            return this;
        }

        if (this.getRight() != null) {
            resNode = this.getRight().midOrderSearch(node);
        }

        return resNode;
    }

    /**
     * 中序查找
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

        if (this.getRight() != null) {
            resNode = this.getRight().postOrderSearch(node);
        }

        System.out.println("进入后序查找！");
        if (this.getData().compareTo(node.getData()) == 0) {
            return this;
        }

        return resNode;
    }

    /**
     * 递归删除节点
     * <p>
     * 1.因为二叉树是单向的，所有应该判断当前节点的子节点是否需要删除，而不能判断当前节点是否需要删除
     * 2.如果当前节点的左子节点不为空，且左子节点就是要删除的节点，就将left置空，并且返回
     * 3.如果当前节点的右子节点不为空，且右子节点就是要删除的节点，就将right置空
     * 4.如果第2、3步没有删除，分别向左、右递归
     */
    public void delete(TreeNode<T> node) {
        if (this.getLeft() != null && this.getLeft().getData().compareTo(node.getData()) == 0) {
            this.left = null;
            return;
        }

        if (this.getRight() != null && this.getRight().getData().compareTo(node.getData()) == 0) {
            this.right = null;
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
