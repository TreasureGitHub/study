package com.ffl.study.java.sort;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/07/07 23:38
 *
 * 基数排序
 */
public class RadixSort {

    public static void main(String[] args) {
        int[] arr = {53, 3, 542, 748, 14, 214};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        // 计算最大值
        int maxValue = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > maxValue) {
                maxValue = arr[i];
            }
        }

        // 最大长度
        int maxLength = String.valueOf(maxValue).length();

        // 排序使用的桶 即 记录桶水位的数组
        int[][] buckets = new int[10][arr.length];
        int[] bucketCnt = new int[10];

        for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {
            for (int j = 0; j < arr.length; j++) {
                int digitOfEle = arr[j] / n % 10;

                // 将数据放入桶中
                buckets[digitOfEle][bucketCnt[digitOfEle]] = arr[j];
                bucketCnt[digitOfEle]++;
            }

            // 从桶中 拿出数据一次放入 原数组中
            int index = 0;
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < bucketCnt[j]; k++) {
                    arr[index] = buckets[j][k];
                    index++;
                }
                // 清除桶计数
                bucketCnt[j] = 0;
            }
        }
    }

}
