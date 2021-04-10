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

    /**
     * 插入排序
     *
     * @param arr
     */
    public static void sort(int[] arr) {

        //待插入index 和 value
        int insertIndex;
        int insertValue;

        for (int i = 1; i < arr.length; i++) {

            // 待插入数组和值
            insertIndex = i;
            insertValue = arr[i];

            for (int j = i; j > 0; j--) {
                // 如果发现比较值值比前一个值小，则将前一个值向后移
                if (insertValue < arr[j - 1]) {
                    arr[j] = arr[j - 1];
                    // 没有立即将 比较值 和 前一位交换，而是记录下位置
                    insertIndex = j - 1;
                } else {
                    break;
                }
            }

            // 如果待插入位置和i不相等，则进行赋值
            if (insertIndex != i) {
                arr[insertIndex] = insertValue;
            }
        }
    }

    /**
     * 另外一种写法 和 sort 方法理解一致
     *
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

    /**
     * 排序排序
     *
     * @param arr
     */
    public static void sort2(int[] arr) {

        // 存放临时数值
        int tmp;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i; j >= 0; j--) {

                // 将待比较数值存如tmp,待比较数值为 arr[j+1]
                tmp = arr[j + 1];

                // 从j+1一次和前面进行比较，如果发现当前位比前一位小，
                // 则将前一位的数值后移，然后将临时值放入前一位
                if (arr[j + 1] < arr[j]) {
                    arr[j + 1] = arr[j];
                    // 立即将 比较值 和 前一位互换
                    arr[j] = tmp;
                } else {
                    // 发现 arr[j + 1] >= arr[j] 后则进行说明数组已经有序，终止本次内循环
                    break;
                }
            }
        }
    }
}
