package com.ffl.study.java.sort;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/06/10 23:34
 * 冒泡排序
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = {2, 5, 1, 3, 8, 5, 7, 4, 3};
        bubbleSort1(arr);
        System.out.println(Arrays.toString(arr));

        // int[] arr1 = {2, 5, 1, 3, 8, 5, 7, 4, 3};
        // bubbleSort2(arr1, arr1.length - 1);
        // System.out.println(Arrays.toString(arr1));
    }

    private static void bubbleSort1(int[] arr) {
        for (int i = 1; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    // private static void bubbleSort2(int[] arr, int n) {
    //     if (n == 0) {
    //         return;
    //     } else {
    //         if (arr[n - 1] > arr[n]) {
    //             swap(arr, n - 1, n);
    //         }
    //
    //         bubbleSort2(arr, n - 1);
    //     }
    //
    // }

    /**
     * 交换位置
     *
     * @param arr
     * @param i
     * @param j
     */
    private static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }
}
