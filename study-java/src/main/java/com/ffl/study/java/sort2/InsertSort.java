package com.ffl.study.java.sort2;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/06/18 23:21
 * <p>
 * 插入排序
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] arr = {1, 2, 5, 1, 3, 8, 5, 7, 4, 3};
        sort1(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * @param arr 待排序数组
     */
    public static void sort(int[] arr) {

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

    /**
     * 第二中写法
     *
     * @param arr 待排序数组
     */
    public static void sort1(int[] arr) {

        for (int i = 1; i < arr.length; i++) {
            // insertIndex为待插入数据位置
            int insertIndex = i;
            // inserValue 为 待插入数据
            int insertValue = arr[i];

            for (int j = i; j >= 1; j--) {
                // 如果值大于前一位，则 前一位的数据后移，并将insertIndex 前移
                if (insertValue < arr[insertIndex - 1]) {
                    // 后移一位
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
}
