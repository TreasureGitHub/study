package com.ffl.study.java.sort;

import com.ffl.study.common.utils.ArrayUtils;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/07/10 00:32
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {6, 1, 2, 6, 6, 6, 3, 8, 6, 6};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        twoWaySort(arr, 0, arr.length - 1);
    }

    /**
     * 双路排序
     *
     * @param arr       数组
     * @param start     开始位置
     * @param end       结束位置
     */
    private static void twoWaySort(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }

        // 选中间值为基准值
        // 将基准值保存在start位置。    将start 和 mid的数值进行交换
        int mid = (start + end) / 2;
        ArrayUtils.swap(arr, start, mid);
        int value = arr[start];

        // 定义低位和高位，start 为保存的值，所以低位从start + 1 开始
        int low = start + 1;
        int high = end;

        while (low < high) {

            // 如果low 位置的值 比 value小，则 low + 1
            while (low <= end && arr[low] < value) {
                low++;
            }

            // 如果high 位置的值 比 value 大，则high -1
            while (high > start && arr[high] > value) {
                high--;
            }

            // 可能存在 low >= high,此时退出循环
            if (low >= high) {
                break;
            }

            // 将 low 和 high 的数据交换
            ArrayUtils.swap(arr, low, high);

            // low + 1,high - 1
            low++;
            high--;

        }

        // high位置的值一定小于等于 value，因此用high进行交换
        ArrayUtils.swap(arr, start, high);

        twoWaySort(arr, start, high - 1);
        twoWaySort(arr, high + 1, end);
    }

    /**
     * 单路排序
     *
     * @param arr
     * @param start
     * @param end
     */
    public static void oneWaySort(int[] arr, int start, int end) {

    }


}
