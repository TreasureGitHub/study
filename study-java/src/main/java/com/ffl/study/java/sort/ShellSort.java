package com.ffl.study.java.sort;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/07/06 00:46
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] arr = {8, 5, 7, 4, 3, 2, 6, 1};

        sort2(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 希尔排序
     *
     * @param arr
     */
    public static void sort(int[] arr) {

        // 待插入index和value
        int insertIndex;
        int insertValue;

        // {8, 5, 7, 4, 3, 2, 6, 1}  gap为步长  4 -> 2 -> 1
        for (int gap = arr.length / 2; gap >= 1; gap = gap / 2) {

            // 待排序的起始位置
            for (int i = gap; i < arr.length; i++) {
                insertIndex = i;
                insertValue = arr[i];

                // 可以理解为和插入排序一样，插入排序的步长为1，而此时变成了gap
                for (int j = i; j - gap >= 0; j -= gap) {
                    if (arr[j - gap] > insertValue) {
                        insertIndex = j - gap;
                        arr[j] = arr[j - gap];
                    } else {
                        break;
                    }
                }

                if (insertIndex != i) {
                    arr[insertIndex] = insertValue;
                }
            }
        }
    }

    /**
     * 希尔排序，对应插入排序中的 sort2方法改写
     *
     * @param arr
     */
    public static void sort2(int[] arr) {
        int tmp;
        // 步长
        for (int gap = arr.length / 2; gap >= 1; gap = gap / 2) {
            // 循环遍历
            for (int i = 0; i < arr.length; i++) {

                //此时步长已经为gap
                for (int j = i; j >= 0 && j + gap < arr.length; j = j - gap) {
                    tmp = arr[j + gap];
                    if (arr[j + gap] < arr[j]) {
                        arr[j + gap] = arr[j];
                        arr[j] = tmp;
                    } else {
                        break;
                    }
                }
            }
        }
    }
}