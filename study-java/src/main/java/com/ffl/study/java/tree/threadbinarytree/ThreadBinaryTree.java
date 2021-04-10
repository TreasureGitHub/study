package com.ffl.study.java.tree.threadbinarytree;

import lombok.Data;

import static com.ffl.study.java.tree.threadbinarytree.NodeTypeEnum.NODE;
import static com.ffl.study.java.tree.threadbinarytree.NodeTypeEnum.TREE;

/**
 * @author lff
 * @datetime 2020/12/25 20:52
 */
@Data
public class ThreadBinaryTree<T extends Comparable<T>> {

    /**
     * 根节点
     */
    private ThreadTreeNode<T> root;

    private ThreadTreeNode pre = null;

    public void midThreadedNode() {
        midThreadedNode(root);
    }

    /**
     * 线索化二叉树
     *
     * @param node
     */
    private void midThreadedNode(ThreadTreeNode<T> node) {
        if (node == null) {
            return;
        }

        // 1.线索化左子树
        midThreadedNode(node.getLeft());
        // 2.线索化当前节点
        // 2.1处理前驱节点
        if (node.getLeft() == null) {
            node.setLeft(pre);
            node.setLeftType(NODE);
        }

        // 2.2处理后继节点
        if (pre != null && pre.getRight() == null) {
            // 让前驱节点的右指针指向当前节点
            pre.setRight(node);
            pre.setRightType(NODE);
        }

        // 没处理一个节点后让当前节点指向前驱节点
        pre = node;

        // 3.线索化右子树
        midThreadedNode(node.getRight());
    }

    /**
     * 中序遍历 线索化二叉树
     */
    public void midOrder() {
        ThreadTreeNode node = root;
        while (node != null) {
            // 找到leftType 为 NODE的节点，说明该节点为按照线索化处理后的有效节点

            while (node.getLeftType() == TREE) {
                node = node.getLeft();
            }

            System.out.println(node);

            while (node.getRightType() == NODE) {
                node = node.getRight();
                System.out.println(node);
            }

            node = node.getRight();
        }
    }
}