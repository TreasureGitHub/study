package com.ffl.study.java.sort;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/06/14 10:53
 * <p>
 * 选择排序
 */
public class SelectSort {

    public static void main(String[] args) {
        int[] arr = {34, 119, 101, 1};

        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            int minValue = arr[i];

            for (int j = i + 1; j < arr.length; j++) {
                if (minValue > arr[j]) {
                    minValue = arr[j];
                    minIndex = j;
                }
            }

            if (i != minIndex) {
                arr[minIndex] = arr[i];
                arr[i] = minValue;
            }
        }
    }
}
