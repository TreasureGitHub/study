package com.ffl.study.java.sort;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/06/22 00:27
 */
public class RadixSort {

    public static void main(String[] args) {
        int[] arr = {53, 3, 542, 748, 14, 214};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * @param arr 待排序数组
     */
    public static void sort(int[] arr) {

        // 得到数组中最大数
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        // 得到是几位数
        int maxLength = String.valueOf(max).length();


        // 1.定义2位数组表示10个桶
        // 2.为防止数据溢出，每隔一维数组的长度为 arr.length
        // 3.基数排序为空间换时间的经典案例
        int[][] bucket = new int[10][arr.length];

        // 为了记录每个桶中实际存放了多少数据，我们定义一个一维数组记录各个桶每次放入个数
        int[] bucketEleCnt = new int[10];

        for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {
            for (int j = 0; j < arr.length; j++) {
                // 取出每个元素个位数
                int digitOfEle = arr[j] / n % 10;
                // 将数据放入桶中
                bucket[digitOfEle][bucketEleCnt[digitOfEle]] = arr[j];
                // 记录数加1
                bucketEleCnt[digitOfEle]++;
            }

            // 按照桶的顺序，将桶中数据放入原来的数组
            int index = 0;
            for (int j = 0; j < bucket.length; j++) {
                // 如果桶中有数据，才遍历
                if (bucketEleCnt[j] > 0) {
                    for (int k = 0; k < bucketEleCnt[j]; k++) {
                        // 取出元素放入 桶中
                        arr[index] = bucket[j][k];
                        index++;
                    }
                    // 记录桶的数据清零
                    bucketEleCnt[j] = 0;
                }
            }
        }
    }
}
