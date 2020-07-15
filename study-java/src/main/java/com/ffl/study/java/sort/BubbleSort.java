package com.ffl.study.java.sort;

import com.ffl.study.common.utils.ArrayUtils;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/07/05 23:21
 * <p>
 * 冒泡排序
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = {6, 3, 4, 8, 9, -1, 5};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 基础版本
     *
     * @param arr 待排序数组
     */
    public static void sort(int[] arr) {
        // 数组最后值 不需要参与排序，因此结束位置为 arr.length - 1

        // int temp;
        for (int i = 0; i < arr.length - 1; i++) {

            for (int j = 0; j < arr.length - 1 - i; j++) {

                // 当前大于后一个，则交换
                if (arr[j] > arr[j + 1]) {
                    ArrayUtils.swap(arr, j, j + 1);


                    // arr[j] = arr[j] ^ arr[j + 1];
                    // arr[j + 1] = arr[j] ^ arr[j + 1];
                    // arr[j] = arr[j] ^ arr[j + 1];

                    // temp = arr[j];
                    // arr[j] = arr[j + 1];
                    // arr[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 改进版本
     * 如果一次排序中发现没有进行交换，说明数组为有序的，此时 停止交换
     *
     * @param arr 待排序数组
     */
    public static void improveSort(int[] arr) {

        // 是否有进行过交换，默认为false，如果没有进行过交换，则表示已经有序了
        boolean hasSwap;

        // 数组最后值 不需要参与排序，因此结束位置为 arr.length - 1
        for (int i = 0; i < arr.length - 1; i++) {

            hasSwap = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {

                // 当前大于后一个，则交换
                if (arr[j] > arr[j + 1]) {
                    ArrayUtils.swap(arr, j, j + 1);
                    hasSwap = true;
                }
            }

            if (!hasSwap) {
                break;
            }
        }
    }
}
