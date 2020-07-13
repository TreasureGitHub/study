package com.ffl.study.java.sort;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/07/05 23:28
 * <p>
 * 插入排序
 */
public class InsertSort {
    public static void main(String[] args) {
        int[] arr = {6, 3, 4, 8, 9, -1, 5};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {

        int insertIndex;
        int insertValue;

        for (int i = 1; i < arr.length; i++) {

            insertValue = arr[i];
            insertIndex = i;

            for (int j = i; j > 0; j--) {
                if (insertValue < arr[j - 1]) {
                    arr[j] = arr[j - 1];
                    insertIndex = j - 1;
                } else {
                    break;
                }
            }

            if (insertIndex != i) {
                arr[insertIndex] = insertValue;
            }
        }
    }

    /**
     * @param arr 待排序数组
     */
    public static void sort1(int[] arr) {

        for (int i = 1; i < arr.length; i++) {
            // insertIndex 为 待插入位置的前一位
            int insertIndex = i - 1;
            // inserValue 为 待插入数据
            int insertValue = arr[i];

            while (insertIndex >= 0 && insertValue < arr[insertIndex]) {
                // 后移一位  insertIndex
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }

            if (insertIndex + 1 != i) {
                arr[insertIndex + 1] = insertValue;
            }
        }
    }
}
