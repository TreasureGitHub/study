package com.ffl.study.java.sort1;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/06/11 00:28
 * <p>
 * 归并排序
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] arr = {8, 4, 5, 7, 1, 3, 6, 2};
        sort(arr);

        System.out.println(Arrays.toString(arr));

    }

    public static void sort(int[] arr) {
        int[] temp = new int[arr.length];
        mergeSort(arr, 0, arr.length - 1, temp);
    }

    private static void mergeSort(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            // 中间的索引
            int mid = (left + right) / 2;
            // 左递归分解
            mergeSort(arr, left, mid, temp);
            // 右递归分解
            mergeSort(arr, mid + 1, right, temp);
            // 合并
            merge(arr, left, mid, right, temp);
        }

    }

    /**
     * 合并方法
     *
     * @param arr   排序的原始数组
     * @param left  左边有序序列的索引
     * @param mid   中间索引
     * @param right 右边索引
     * @param temp  做中转的数组
     */
    private static void merge(int[] arr, int left, int mid, int right, int[] temp) {
        int i = left;     // 初始化i，左边有序序列的初始化索引
        int j = mid + 1;  // 初始化j，右边有序序列的初始化索引
        int t = 0;        // t 指向 temp数组的当前索引

        // 1
        // 先将左右两边(有序) 数据按照规则进行填充 到temp 数组
        // 直到 左右两边的有序序列，有一边处理完毕为止
        while (i <= mid && j <= right) {

            if (arr[i] <= arr[j]) {
                temp[t] = arr[i];
                t++;
                i++;
            } else {
                temp[t] = arr[j];
                j++;
                t++;
            }

        }

        // 2
        // 将剩余数据的一边数据依次全部填充到 temp

        // 左边可能还有剩余元素
        while (i <= mid) {
            temp[t] = arr[i];
            t++;
            i++;
        }

        while (j <= right) {
            temp[t] = arr[j];
            t++;
            j++;
        }

        // 3 将temp 数组的值拷贝到arr
        // 注意，并不是每次都拷贝所有
        t = 0;
        int tempLeft = left;

        while (tempLeft <= right) {
            arr[tempLeft] = temp[t];
            t++;
            tempLeft++;
        }

    }

}
