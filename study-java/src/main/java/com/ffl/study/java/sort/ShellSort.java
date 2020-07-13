package com.ffl.study.java.sort;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/07/06 00:46
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] arr = {8, 5, 7, 4, 3, 2, 6, 1};

        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {

        // 待插入index和value
        int insertIndex;
        int insertValue;

        // gap为步长
        for (int gap = arr.length / 2; gap >= 1; gap = gap / 2) {
            for (int i = gap; i < arr.length; i++) {
                insertIndex = i;
                insertValue = arr[i];

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
}