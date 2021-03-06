package com.ffl.study.java.tree.threadbinarytree;

/**
 * @author lff
 * @datetime 2020/12/25 23:54
 */
public class ThreadBinaryTreeTest {

    public static void main(String[] args) {
        ThreadTreeNode<Integer> node2 = new ThreadTreeNode<>(2);
        ThreadTreeNode<Integer> node4 = new ThreadTreeNode<>(4);
        ThreadTreeNode<Integer> node5 = new ThreadTreeNode<>(5);
        ThreadTreeNode<Integer> node6 = new ThreadTreeNode<>(6);
        ThreadTreeNode<Integer> node7 = new ThreadTreeNode<>(7);
        ThreadTreeNode<Integer> node8 = new ThreadTreeNode<>(8);
        ThreadTreeNode<Integer> node9 = new ThreadTreeNode<>(9);

        node6.setLeft(node4);
        node6.setRight(node8);

        node4.setLeft(node2);
        node4.setRight(node5);

        node8.setLeft(node7);
        node8.setRight(node9);

        ThreadBinaryTree threadBinaryTree = new ThreadBinaryTree();
        threadBinaryTree.setRoot(node6);
        threadBinaryTree.midThreadedNode();

        // node5号节点测试
        System.out.println(node5.getLeft());  // 4
        System.out.println(node5.getRight()); // 6


        System.out.println("---------------------线索化方式遍历二叉树");
        threadBinaryTree.midOrder();

    }
}
