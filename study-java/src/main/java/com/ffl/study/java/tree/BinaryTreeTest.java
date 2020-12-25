package com.ffl.study.java.tree;


/**
 * @author lff
 * @datetime 2020/06/24 00:55
 */
public class BinaryTreeTest {

    public static void main(String[] args) {

        TreeNode<Integer> treeNode2 = new TreeNode<>(2);
        TreeNode<Integer> treeNode4 = new TreeNode<>(4);
        TreeNode<Integer> treeNode5 = new TreeNode<>(5);
        TreeNode<Integer> treeNode6 = new TreeNode<>(6);
        TreeNode<Integer> treeNode7 = new TreeNode<>(7);
        TreeNode<Integer> treeNode8 = new TreeNode<>(8);
        TreeNode<Integer> treeNode9 = new TreeNode<>(9);

        // TreeNode<Integer> treeNode1 = new TreeNode<>(1);

        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.setRoot(treeNode6);
        treeNode6.setLeft(treeNode4);
        treeNode6.setRight(treeNode8);
        treeNode4.setLeft(treeNode2);
        treeNode4.setRight(treeNode5);
        treeNode8.setLeft(treeNode7);
        treeNode8.setRight(treeNode9);

        System.out.println("---------------前序--------------");
        //6->4->2->5->8->7->9
        tree.preOrder();

        System.out.println();
        System.out.println("---------------中序--------------");
        //2->4->5->6->7->8->9
        tree.midOrder();

        System.out.println();
        System.out.println("---------------后续--------------");
        //2->5->4->7->9->8->6
        tree.postOrder();
        System.out.println();


        System.out.println("---------前序查找--------");
        TreeNode<Integer> preOrderSearch = tree.preOrderSearch(treeNode5);
        System.out.println(preOrderSearch);

        System.out.println("---------中序查找--------");
        TreeNode<Integer> midOrderSearch = tree.midOrderSearch(treeNode2);
        System.out.println(midOrderSearch);

        System.out.println("---------后序查找--------");
        TreeNode<Integer> postOrderSearch = tree.postOrderSearch(treeNode5);
        System.out.println(postOrderSearch);

        System.out.println("---------删除前--------");
        tree.preOrder();
        // tree.delete(treeNode4);
        System.out.println();
        System.out.println("---------删除后--------");
        tree.preOrder();

        System.out.println();
        Integer[] arr = {6,4,8,2,5,7,9};
        // ArrBinaryTree<Integer> arrBinaryTree= new ArrBinaryTree<>(arr);
        // arrBinaryTree.preOrder();
    }


}