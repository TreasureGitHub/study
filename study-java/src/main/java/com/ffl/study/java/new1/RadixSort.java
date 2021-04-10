package com.ffl.study.java.new1;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/12/26 17:35
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

        // 排序使用的数组
        int[][] buckets = new int[10][maxLength];
        int[] bucketCnt = new int[10];

        for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {

            // 将数据放入桶中
            for (int j = 0; j < arr.length; j++) {
                // 相应位数的数值
                int digitOfEle = arr[j] / n % 10;

                // 将数据放入桶中
                buckets[digitOfEle][bucketCnt[digitOfEle]] = arr[j];
                // 计数+1
                bucketCnt[digitOfEle]++;
            }

            // 从桶中 拿出数据依次放入 原数组中
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
