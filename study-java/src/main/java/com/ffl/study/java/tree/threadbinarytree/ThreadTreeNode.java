package com.ffl.study.java.tree.threadbinarytree;

import lombok.Data;

/**
 * @author lff
 * @datetime 2020/12/25 23:35
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
     * 左节点类型
     */
    private NodeTypeEnum leftType;

    /**
     * 右节点
     */
    private ThreadTreeNode<T> right;

    /**
     * 右节点类型
     */
    private NodeTypeEnum rightType;

    public ThreadTreeNode(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "data=" + data +
                '}';
    }
}
