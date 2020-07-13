package com.ffl.study.java.tree.threadbinarytree;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lff
 * @datetime 2020/06/30 01:01
 */
public class ThreadBinaryTree<T extends Comparable<T>> {

    private static final int ONE = 1;

    private static final int ZERO = 0;


    /**
     * pre总是保留前一个节点
     */
    private ThreadTreeNode<T> pre = null;

    /**
     * 根节点
     */
    @Getter
    @Setter
    private ThreadTreeNode<T> root;

    public void threadedNode(){
        this.threadedNode(root);
    }

    /**
     * 中序遍历线索化二叉树
     */
    public void midOrder(){
        //
        ThreadTreeNode node = root;
        while (node != null){
            while(node.getLeftType() == ZERO){
                node = node.getLeft();
            }

            System.out.println(node);

            while(node.getRightType() == ONE){
                node = node.getRight();
                System.out.println(node);
            }

            node = node.getRight();
        }

    }


    /**
     * 线索化
     *
     * @param node
     */
    private void threadedNode(ThreadTreeNode node){
        if(node == null){
            return ;
        }

        // 线索化左节点
        threadedNode(node.getLeft());

        // 处理当前节点的前驱节点
        if(node.getLeft() == null){
            node.setLeft(pre);
            node.setLeftType(ONE);
        }

        // 处理后驱节点
        if(pre != null && pre.getRight() == null){
            // 让前驱节点的右指针指向当前节点
            pre.setRight(node);
            pre.setRightType(ONE);
        }

        // 每处理一个节点让当前节点是下一个节点的前驱节点
        pre = node;

        // 线索化当前节点
        // 线索化右节点
        threadedNode(node.getRight());
    }
}
