package com.ffl.study.java.oldtree.binarytree;

import com.ffl.study.common.constants.StringConstants;
import com.ffl.study.common.utils.ArrayUtils;

/**
 * @author lff
 * @datetime 2020/06/30 00:36
 */
public class ArrBinaryTree<T> {

    private T[] arr;

    public ArrBinaryTree(T[] arr) {
        this.arr = arr;
    }

    /**
     * 前序遍历
     */
    public void preOrder(){
        this.preOrder(0);
    }

    /**
     * @param index 数组下标
     */
    private void preOrder(int index) {
        if (ArrayUtils.isNullOrEmpty(arr)) {
            return;
        }

        System.out.print(arr[index] + StringConstants.TAB);

        // 向左递归
        if (2 * index + 1 < arr.length) {
            preOrder(2 * index + 1);
        }

        // 向左递归
        if (2 * index + 2 < arr.length) {
            preOrder(2 * index + 2);
        }
    }

    /**
     * 中序遍历
     */
    public void midOrder(){
        this.midOrder(0);
    }

    /**
     * @param index 数组下标
     */
    private void midOrder(int index) {
        if (ArrayUtils.isNullOrEmpty(arr)) {
            return;
        }

        // 向左递归
        if (2 * index + 1 < arr.length) {
            midOrder(2 * index + 1);
        }

        System.out.print(arr[index] + StringConstants.TAB);

        // 向左递归
        if (2 * index + 2 < arr.length) {
            midOrder(2 * index + 2);
        }
    }

    /**
     * 后序遍历
     */
    public void postOrder(){
        this.postOrder(0);
    }

    /**
     * @param index 数组下标
     */
    private void postOrder(int index) {
        if (ArrayUtils.isNullOrEmpty(arr)) {
            return;
        }

        // 向左递归
        if (2 * index + 1 < arr.length) {
            postOrder(2 * index + 1);
        }

        // 向左递归
        if (2 * index + 2 < arr.length) {
            postOrder(2 * index + 2);
        }

        System.out.print(arr[index] + StringConstants.TAB);
    }
}
