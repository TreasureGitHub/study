package com.ffl.study.java.sort;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/06/22 00:03
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] arr = {8, 4, 5, 7, 1, 3, 6, 2};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        int[] temp = new int[arr.length];
        mergeSort(arr,0,arr.length-1,temp);
    }

    private static void mergeSort(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            // 向左递归分解
            mergeSort(arr,left,mid,temp);
            // 向右递归分解
            mergeSort(arr,mid+1,right,temp);
            // 合并
            merge(arr,left,mid,right,temp);
        }


    }

    /**
     * @param arr   待排序数组
     * @param left  左边有序序列初始索引
     * @param mid   中间索引
     * @param right 右边索引
     * @param temp  中转数组
     */
    public static void merge(int[] arr, int left, int mid, int right, int[] temp) {
        // 左边有序序列初始索引
        int i = left;
        // 右边有序序列初始索引
        int j = mid + 1;

        // temp数组的初始索引
        int t = 0;

        // 先将左右两边数据按规填充到temp
        // 直到左右两边有序序列处理完成为止
        while (i <= mid && j <= right) {
            // 左边元素小于等于 小于等于右边 有序序列 当前元素
            if (arr[i] <= arr[j]) {
                temp[t] = arr[i];
                i++;
                t++;
            } else {
                temp[t] = arr[j];
                j++;
                t++;
            }
        }

        // 将有剩余数据一边的数据填充到temp
        while (i <= mid) {
            temp[t] = arr[i];
            i++;
            t++;
        }

        while (j <= right) {
            // 左边元素小于等于 小于等于右边 有序序列 当前元素
            temp[t] = arr[j];
            j++;
            t++;
        }

        // 将temp 数组元素拷贝到arr
        t = 0;
        int tempLeft = left;
        while (tempLeft <= right) {
            arr[tempLeft] = temp[t];
            t++;
            tempLeft++;
        }

        System.out.println(Arrays.toString(temp));


    }
}
