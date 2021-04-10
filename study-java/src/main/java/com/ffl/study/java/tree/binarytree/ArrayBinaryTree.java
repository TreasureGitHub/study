package com.ffl.study.java.tree.binarytree;

import com.ffl.study.common.utils.ArrayUtils;

/**
 * @author lff
 * @datetime 2020/12/25 23:10
 */
public class ArrayBinaryTree {

    public static void main(String[] args) {
        int[] arr = {6, 4, 8, 2, 5, 7, 9};
        ArrayBinaryTree arrayBinaryTree = new ArrayBinaryTree(arr);
        System.out.println("---------前序遍历");
        arrayBinaryTree.preOrder();
        System.out.println("---------中序遍历");
        arrayBinaryTree.midOrder();
        System.out.println("---------后序遍历");
        arrayBinaryTree.postOrder();
    }

    private int[] arr;

    public ArrayBinaryTree(int[] arr) {
        this.arr = arr;
    }

    public void preOrder() {
        preOrder(0);
    }

    public void midOrder() {
        midOrder(0);
    }

    public void postOrder() {
        postOrder(0);
    }


    /**
     * 前序遍历
     *
     * @param index 下标
     */
    private void preOrder(int index) {
        if (ArrayUtils.isNullOrEmpty(arr)) {
            System.out.println("数组为空，不能进行遍历！");
        }

        System.out.println(arr[index]);

        /**
         * 左递归遍历
         */
        if (2 * index + 1 < arr.length) {
            preOrder(2 * index + 1);
        }

        // 右递归遍历
        if (2 * index + 2 < arr.length) {
            preOrder(2 * index + 2);
        }
    }

    /**
     * 中序遍历
     *
     * @param index 下标
     */
    private void midOrder(int index) {
        if (ArrayUtils.isNullOrEmpty(arr)) {
            System.out.println("数组为空，不能进行遍历！");
        }

        /**
         * 左递归遍历
         */
        if (2 * index + 1 < arr.length) {
            midOrder(2 * index + 1);
        }

        System.out.println(arr[index]);

        // 右递归遍历
        if (2 * index + 2 < arr.length) {
            midOrder(2 * index + 2);
        }
    }

    /**
     * 后序遍历
     *
     * @param index 下标
     */
    private void postOrder(int index) {
        if (ArrayUtils.isNullOrEmpty(arr)) {
            System.out.println("数组为空，不能进行遍历！");
        }

        /**
         * 左递归遍历
         */
        if (2 * index + 1 < arr.length) {
            postOrder(2 * index + 1);
        }

        // 右递归遍历
        if (2 * index + 2 < arr.length) {
            postOrder(2 * index + 2);
        }

        System.out.println(arr[index]);
    }
}
