package com.ffl.study.java.sort;

import com.ffl.study.common.utils.ArrayUtils;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/06/21 12:29
 * <p>
 * 冒泡排序
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] arr = {8, 5, 7, 4, 3, 2, 6, 1};
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        int insertIndex;
        int insertValue;
        for (int gap = arr.length / 2; gap >= 2; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {

                insertIndex = i;
                insertValue = arr[i];
                for (int j = i; j - gap >= 0; j -= gap) {
                    if (insertValue < arr[j - gap]) {
                        arr[j] = arr[j - gap];
                        insertIndex = j - gap;
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

    public static void sort1(int[] arr) {

        for (int gap = arr.length / 2; gap >= 1; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                for (int j = i; j - gap >= 0; j -= gap) {
                    if (arr[j] < arr[j - gap]) {
                        ArrayUtils.swap(arr, j, j - gap);
                    }
                }
            }
        }
    }
}
