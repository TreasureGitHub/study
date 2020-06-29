package com.ffl.study.java.sort1;

import com.ffl.study.common.utils.ArrayUtils;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/05/27 10:05
 * <p>
 * https://www.runoob.com/w3cnote/quick-sort.html
 * https://blog.csdn.net/nrsc272420199/article/details/82587933
 * <p>
 * 快速排序
 */
public class QuickSort {

    public static void main(String[] args) {
        // int arr[] = {-9, 78, 0, 23, -567, 70};
        int[] arr = {6, 1, 2, 6, 6, 6, 3, 8, 6, 6};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int left, int right) {
        int low = left;
        int high = right;
        int pivot = arr[(left + right) / 2];

        // 目的是让 比 pivot 值小的放左边
        while (low < high) {

            while (arr[low] < pivot) {
                low += 1;
            }

            while (arr[high] > pivot) {
                high -= 1;
            }

            // 如果low >= high r说明 pivot左边的值全部小于等于piovt，右边的值全部大于pivot
            if (low >= high) {
                break;
            }

            // 交换
            ArrayUtils.swap(arr, low, high);

            // 如果交换完后，发现arr[low] == pivot 相当于 high-- 前移
            if (arr[low] == pivot) {
                high -= 1;
            }

            // 如果交换完后，发现arr[low] == pivot 相当于 low++ 前移
            if (arr[low] == pivot) {
                low += 1;
            }
        }

        if (low == high) {
            low += 1;
            high -= 1;
        }

        // 向左递归
        if (left < high) {
            quickSort(arr, left, high);
        }

        // 向右递归
        if (right > low) {
            quickSort(arr, low, right);
        }
    }
}
