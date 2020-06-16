package com.ffl.study.java.sort;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/06/14 11:28
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] arr = {101, 34, 119, 1};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        // 定义待插入的数据
        for (int i = 1; i < arr.length; i++) {

            int value = arr[i];
            int insertIndex = i - 1;

            for (int j = i - 1; j >= 0; j--) {
                // 如果指定位置的数值大于value，则将指定位置数据后移一位
                if (arr[j] > value) {
                    arr[j + 1] = arr[j];
                    insertIndex = j;
                } else {
                    insertIndex = j + 1;
                    break;
                }
            }
            arr[insertIndex] = value;

            // int value = arr[i];
            // int insertIndex = i - 1;
            //
            // while(insertIndex >= 0 && arr[insertIndex] > value){
            //     arr[insertIndex + 1] = arr[insertIndex];
            //     insertIndex -- ;
            // }
            // arr[insertIndex + 1] = value;
        }
    }
}
