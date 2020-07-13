package com.ffl.study.java.tree.threadbinarytree;

import lombok.Data;

/**
 * @author lff
 * @datetime 2020/06/30 22:08
 */
@Data
public class ThreadTreeNode<T extends Comparable<T>> {

    /**
     * 数据
     */
    private T data;

    /**
     * 左节点
     */
    private ThreadTreeNode<T> left;

    /**
     * 右节点
     */
    private ThreadTreeNode<T> right;

    /**
     * 0表示指向左子树 1 表示指向前驱节点
     */
    private int leftType;

    /**
     * 0表示指向左子树 1 表示指向后继节点
     */
    private int rightType;

    public ThreadTreeNode(T data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "ThreadTreeNode{" +
                "data=" + data +
                '}';
    }
}
