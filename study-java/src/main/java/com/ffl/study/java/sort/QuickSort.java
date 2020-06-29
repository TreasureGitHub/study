package com.ffl.study.java.sort;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/06/21 19:30
 * <p>
 * https://www.runoob.com/w3cnote/quick-sort.html
 */
public class QuickSort {

    public static void main(String[] args) {
        // int[] arr = {1, 5, 7, 4, 9, 2, 6, 8};

        // int[] arr = {1, 5, 7, 4, 4, 9, 2, 6, 4, 8};

        int[] arr = {6, 1, 2, 6, 6, 6, 3, 8, 6, 6};

        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    public static void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int low = left;
            int high = right;

            // 要排序的基准值
            int value = arr[left];

            // 当左边小于右边，循环
            while (low < high) {
                // 从右向左找第一个小于value的数
                while (low < high && arr[high] >= value) {
                    high--;
                }

                // 将high的值赋值给low ，low + 1
                if (low < high) {
                    arr[low] = arr[high];
                    low++;
                }

                // 从左往右找到第一个大于value的值
                while (low < high && arr[low] < value) { // 从左向右找第一个大于等于x的数
                    low++;
                }

                // 将low的值赋值给 high， high --
                if (low < high) {
                    arr[high] = arr[low];
                    high--;
                }
            }

            // 最后将value的值赋值给low
            arr[low] = value;

            // 递归调用
            quickSort(arr, left, low - 1); // 递归调用
            quickSort(arr, low + 1, right);
        }

    }
}
