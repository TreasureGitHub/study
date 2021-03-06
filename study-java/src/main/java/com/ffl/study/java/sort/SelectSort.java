package com.ffl.study.java.sort;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/07/05 23:28
 * <p>
 * 选择排序
 */
public class SelectSort {

    public static void main(String[] args) {
        int[] arr = {6, 3, 4, 8, 9, -1, 5};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 选择排序
     *
     * @param arr 待排序数组
     */
    public static void sort(int[] arr) {
        int minIndex;
        int minValue;
        for (int i = 0; i < arr.length - 1; i++) {
            // 默认最小值和最小值位置
            minIndex = i;
            minValue = arr[i];

            // 从i位置的后一位和最小值进行比较
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < minValue) {
                    minValue = arr[j];
                    minIndex = j;
                }
            }

            // 如果发现 最小值 不为 初始化的位置才进行交换
            if (minIndex != i) {
                arr[minIndex] = arr[i];
                arr[i] = minValue;
            }
        }

    }
}
