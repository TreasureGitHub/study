package com.ffl.study.java.new1;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/12/26 14:28
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = {9, 3, 8, 5, 1};
        // baseSort(arr);
        // sort1(arr);
        sort2(arr);
        System.out.println(Arrays.toString(arr));
    }


    public static void baseSort(int[] arr) {
        int tmp;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }

    public static void sort1(int[] arr) {
        // 是否有进行过交换，默认为false，如果没有进行过交换，则表示已经有序了
        boolean hasSwap;
        int tmp;
        for (int i = 0; i < arr.length - 1; i++) {
            hasSwap = false;
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                    hasSwap = true;
                }
            }

            if (!hasSwap) {
                break;
            }
        }
    }

    /**
     * 如果 当前循环 最后 交换位置为 index ,则下次只需要 遍历至 index - 1
     *
     * @param arr
     */
    public static void sort2(int[] arr) {
        // 分别定义最后交换的位置和当前交换的位置
        int lastSwapIndex, currSwapIndex = arr.length - 1;
        int tmp;

        while (currSwapIndex > 0) {
            lastSwapIndex = currSwapIndex;

            currSwapIndex = 0;
            for (int i = 0; i < lastSwapIndex; i++) {
                if (arr[i] > arr[i + 1]) {
                    tmp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = tmp;
                    currSwapIndex = i;
                }
            }

        }

    }

}
