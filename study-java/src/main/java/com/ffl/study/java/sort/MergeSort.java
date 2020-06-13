package com.ffl.study.java.sort;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/06/11 00:28
 * <p>
 * 归并排序
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 10};
        int[] newArr = mergeSort(arr, 0, arr.length - 1);

        System.out.println(Arrays.toString(newArr));
    }

    public static int[] mergeSort(int[] nums, int l, int h) {
        if (l == h)
            return new int[]{nums[l]};

        int mid = l + (h - l) / 2;
        int[] leftArr = mergeSort(nums, l, mid); //左有序数组
        int[] rightArr = mergeSort(nums, mid + 1, h); //右有序数组
        int[] newNum = new int[leftArr.length + rightArr.length]; //新有序数组

        int m = 0, i = 0, j = 0;
        while (i < leftArr.length && j < rightArr.length) {
            newNum[m++] = leftArr[i] < rightArr[j] ? leftArr[i++] : rightArr[j++];
        }
        while (i < leftArr.length)
            newNum[m++] = leftArr[i++];
        while (j < rightArr.length)
            newNum[m++] = rightArr[j++];
        return newNum;
    }

}
