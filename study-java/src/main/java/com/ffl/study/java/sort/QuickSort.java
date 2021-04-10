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
        // int[] arr1 = {9, 8, 8, 3, 4, 5};
        int[] arr1 = {1, 5, 0, 1, 9, 2, 6, 8};
        // sort1(arr);
        sort1(arr1);
        // System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(arr1));
    }

    public static void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    public static void sort1(int[] arr) {
        sort1(arr, 0, arr.length - 1);
    }

    /**
     * 快排
     *
     * @param arr   数组
     * @param start 开始位置
     * @param end   结束位置
     */
    private static void sort(int[] arr, int start, int end) {
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

        sort(arr, start, high - 1);
        sort(arr, high + 1, end);
    }

    /**
     * 排序 选中间值为基准值
     *
     * @param arr
     * @param left
     * @param right
     */
    public static void sort1(int[] arr, int left, int right) {
        int low = left; //左下标
        int high = right; //右下标

        //pivot 中轴值
        int pivot = arr[(left + right) / 2];

        //临时变量，作为交换时使用
        int temp = 0;
        // while 循环的目的是让比 pivot 值小放到左边
        // 比 pivot 值大放到右边
        while (low < high) {
            //在 pivot 的左边一直找,找到大于等于 pivot 值,才退出
            while (arr[low] < pivot) {
                low += 1;
            }
            //在 pivot 的右边一直找,找到小于等于 pivot 值,才退出
            while (arr[high] > pivot) {
                high -= 1;
            }

            //如果 l >= r 说明 pivot 的左右两的值，已经按照左边全部是 小于等于 pivot 值，右边全部是大于等于 pivot 值 退出循环
            if (low >= high) {
                break;
            }

            //交换
            temp = arr[low];
            arr[low] = arr[high];
            arr[high] = temp;

            //如果交换完后，发现 arr[low] == pivot 值 相等 high--， 前移
            //当数组中有几个值相等时。如果此处不加该判断条件，则永远退出不了循环
            if (arr[low] == pivot) {
                high--;
            }

            // //如果交换完后，发现 arr[high] == pivot 值 相等 low++， 后移
            // //当数组中有几个值相等时。如果此处不加该判断条件，则永远退出不了循环
            if (arr[high] == pivot) {
                low++;
            }

            System.out.println(Arrays.toString(arr));
        }

        // 如果 low == high, 必须 low++, high--, 否则为出现栈溢出
        if (low == high) {
            low += 1;
            high -= 1;
        }

        //向左递归
        if (left < high) {
            sort1(arr, left, high);
        }

        //向右递归
        if (right > low) {
            sort1(arr, low, right);
        }
    }
}
