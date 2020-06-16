package com.ffl.study.java.sort;

import com.ffl.study.common.utils.ArrayUtils;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/06/14 18:03
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
        System.out.println(Arrays.toString(arr));
        swapSort(arr);
        // System.out.println(Arrays.toString(arr));
    }

    // 使用逐步推导的方式来编写希尔排序
    public static void swapSort(int[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                // 遍历各组中所有元素(共gap组)，步长为gap
                for (int j = i - gap; j >= 0; j -= gap) {
                    // 如果当前元素大于交换步长后的那个元素，说明交换
                    if (arr[j] > arr[j + gap]) {
                        ArrayUtils.swap(arr, j, j + gap);
                    }
                }
            }
            // System.out.println(Arrays.toString(arr));
        }
    }

    // 使用逐步推导的方式来编写希尔排序
    public static void shiftSort(int[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                int temp = arr[j];
                if (arr[j] < arr[j - gap]) {
                    while (j - gap >= 0 && temp < arr[j - gap]) {
                        // 移动
                        arr[j] = arr[j - gap];
                        j -= gap;
                    }
                    arr[j] = temp;
                }
            }
            // System.out.println(Arrays.toString(arr));
        }
    }
}
