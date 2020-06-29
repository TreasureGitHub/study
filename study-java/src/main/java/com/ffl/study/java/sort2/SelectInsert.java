package com.ffl.study.java.sort2;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/06/18 22:12
 *
 * 选择排序
 */
public class SelectInsert {

    public static void main(String[] args) {
        int[] arr = {2, 5, 1, 3, 8, 5, 7, 4, 3};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 排序
     *
     * @param arr 待排序数组
     */
    public static void sort(int[] arr) {

        int minIndex;
        int minValue;

        for (int i = 0; i < arr.length - 1; i++) {

            // 初始值为第i个数组的下标和值
            minIndex = i;
            minValue = arr[i];

            // 从下一个开始和最小值比较
            for (int j = i + 1; j < arr.length; j++) {
                if(arr[j] < minValue){
                    minValue = arr[j];
                    minIndex = j;
                }
            }

            if(minIndex != i){
                arr[minIndex] = arr[i];
                arr[i] = minValue;
            }
        }
    }
}
