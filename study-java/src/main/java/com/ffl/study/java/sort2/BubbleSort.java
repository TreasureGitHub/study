package com.ffl.study.java.sort2;

import com.ffl.study.common.utils.ArrayUtils;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/06/18 21:47
 *
 * 冒泡排序
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = {2, 5, 1, 3, 8, 5, 7, 4, 3};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 冒泡排序
     *
     * @param arr  待排序数组
     */
    public static void sort(int[] arr) {
        // 最后一个值不用排序，故 为arr.length - 1
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                // 当前和后一个进行比较，如果比后一个大，则交换
                if (arr[j] > arr[j + 1]) {
                    ArrayUtils.swap(arr, j, j + 1);
                }
            }
        }
    }

    /**
     * 冒泡排序，优化后
     *
     * @param arr 待排序数组
     */
    public static void sort1(int[] arr) {
        // 最后一个值不用排序，故 为arr.length - 1

        // 用来标识是否已交换
        boolean flag;
        for (int i = 0; i < arr.length - 1; i++) {

            flag = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                // 当前和后一个进行比较，如果比后一个大，则交换
                if (arr[j] > arr[j + 1]) {
                    ArrayUtils.swap(arr, j, j + 1);
                    flag = true;
                }
            }

            // 如果没有发生交换，表示已经是有序，则直接退出
            if(!flag){
                break;
            }
        }
    }
}
