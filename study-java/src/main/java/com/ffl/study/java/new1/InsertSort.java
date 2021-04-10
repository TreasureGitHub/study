package com.ffl.study.java.new1;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/12/26 15:42
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] arr = {6, 3, 4, 8, 9, -1, 5};
        // baseSort(arr);
        // sort1(arr);
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }


    public static void sort(int[] arr) {
        int swapValue;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i; j >= 0; j--) {
                swapValue = arr[j + 1];
                if (arr[j + 1] < arr[j]) {
                    arr[j + 1] = arr[j];
                    arr[j] = swapValue;
                } else {
                    break;
                }
            }
        }
    }

}
