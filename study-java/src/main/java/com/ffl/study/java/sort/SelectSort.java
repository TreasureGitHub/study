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

    public static void sort(int[] arr) {

        int minIndex;
        int minValue;
        for (int i = 0; i < arr.length - 1; i++) {

            minIndex = i;
            minValue = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < minValue) {
                    minValue = arr[j];
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                arr[minIndex] = arr[i];
                arr[i] = minValue;
            }
        }

    }

}
