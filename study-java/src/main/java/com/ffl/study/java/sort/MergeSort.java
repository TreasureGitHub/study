package com.ffl.study.java.sort;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/07/08 00:07
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] arr = {8, 4, 5, 7, 1, 3, 6, 2};
        sort(arr);

        System.out.println(Arrays.toString(arr));

    }

    public static void sort(int[] arr) {
        int[] temp = new int[arr.length];
        sortMerge(arr, 0, arr.length - 1, temp);
    }

    public static void sortMerge(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            sortMerge(arr, left, mid, temp);
            sortMerge(arr, mid + 1, right, temp);
            merge(arr,left,mid,right,temp);
        }
    }


    /**
     * 合并方法
     *
     * @param arr   待排序的原始数组
     * @param left  左边有序序列的索引
     * @param mid   中间索引
     * @param right 右边索引
     * @param temp  临时数组，中转使用
     */
    private static void merge(int[] arr, int left, int mid, int right, int[] temp) {

        int i = left;
        int j = mid + 1;
        int t = 0;

        //1. 先将左右两边(有序) 数据按照规则进行填充 到temp 数组
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[t] = arr[i];
                i++;
            } else {
                temp[t] = arr[j];
                j++;
            }

            t++;
        }

        //2. 将剩余数据的一边数据依次全部填充到 temp
        while (i <= mid) {
            temp[t] = arr[i];
            i++;
            t++;
        }

        while (j <= right) {
            temp[t] = arr[j];
            j++;
            t++;
        }

        // 将 临时数组拷贝至arr
        t = 0;
        int tempLeft = left;
        while (tempLeft <= right) {
            arr[tempLeft] = temp[t];
            t++;
            tempLeft++;
        }
    }
}
