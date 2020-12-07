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
        int[] arr = {9, 3, 8, 5, 1};
        baseSort(arr);
        // System.out.println(Arrays.toString(arr));
    }

    /**
     * 基础版本
     *
     * @param arr 待排序数组
     */
    public static void baseSort(int[] arr) {
        // 数组最后值 不需要参与排序，因此结束位置为 arr.length - 1

        for (int i = 0; i < arr.length - 1; i++) {

            for (int j = 0; j < arr.length - 1 - i; j++) {

                // 当前大于后一个，则交换
                if (arr[j] > arr[j + 1]) {
                    ArrayUtils.swap(arr, j, j + 1);
                }
            }

            System.out.println(Arrays.toString(arr));
        }
    }

    /**
     * 改进版本1
     * 如果一次排序中发现没有进行交换，说明数组为有序的，此时 停止交换
     *
     * @param arr 待排序数组
     */
    public static void sort1(int[] arr) {

        // 是否有进行过交换，默认为false，如果没有进行过交换，则表示已经有序了
        boolean hasSwap;


        int temp;
        // 数组最后值 不需要参与排序，因此结束位置为 arr.length - 1
        for (int i = 0; i < arr.length - 1; i++) {

            hasSwap = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {

                // // 当前大于后一个，则交换
                if (arr[j] > arr[j + 1]) {
                    // ArrayUtils.swap(arr, j, j + 1);
                    temp = arr[j];

                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    hasSwap = true;
                }
            }

            if (!hasSwap) {
                break;
            }
        }
    }

    /**
     * 改进版本二
     * <p>
     * <p>
     * 如果 当前循环 最后 交换位置为 index ,则下次只需要 遍历至 index - 1
     *
     * @param arr
     */
    public static void sort(int[] arr) {

        // 分别定义最后交换的位置和当前交换的位置
        int lastSwapIndex, currSwapIndex = arr.length - 1;

        int temp;
        while (currSwapIndex > 0) {
            lastSwapIndex = currSwapIndex;

            currSwapIndex = 0;
            for (int i = 0; i < lastSwapIndex; i++) {
                if (arr[i] > arr[i + 1]) {
                    // ArrayUtils.swap(arr, i, i + 1);
                    temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;

                    currSwapIndex = i;
                }
            }
        }
    }
}
