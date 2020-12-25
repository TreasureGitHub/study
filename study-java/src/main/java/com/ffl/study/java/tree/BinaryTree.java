package com.ffl.study.java.tree;

import lombok.Data;

/**
 * @author lff
 * @datetime 2020/12/25 20:52
 */
@Data
public class BinaryTree<T extends Comparable<T>> {

    /**
     * 根节点
     */
    private TreeNode<T> root;


    /**
     * 前序遍历
     */
    public void preOrder() {
        if (this.root != null) {
            this.root.preOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    /**
     * 中序遍历
     */
    public void midOrder() {
        if (this.root != null) {
            this.root.midOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    /**
     * 后续遍历
     */
    public void postOrder() {
        if (this.root != null) {
            this.root.postOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    public TreeNode<T> preOrderSearch(TreeNode<T> node){
        if (this.root != null) {
            return this.root.preOrderSearch(node);
        } else {
            System.out.println("二叉树为空，无法遍历");
            return  null;
        }
    }

    public TreeNode<T> midOrderSearch(TreeNode<T> node){
        if (this.root != null) {
            return this.root.midOrderSearch(node);
        } else {
            System.out.println("二叉树为空，无法遍历");
            return  null;
        }
    }

    public TreeNode<T> postOrderSearch(TreeNode<T> node){
        if (this.root != null) {
            return this.root.postOrderSearch(node);
        } else {
            System.out.println("二叉树为空，无法遍历");
            return  null;
        }
    }

}