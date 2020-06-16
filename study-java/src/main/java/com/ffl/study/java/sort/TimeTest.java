package com.ffl.study.java.sort;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/06/14 10:54
 */
public class TimeTest {

    public static void main(String[] args) {
        int[] arr = {2, 5, 1, 3, 8, 5, 7, 4, 3};
        // BubbleSort.sort(arr);
        SelectSort.sort(arr);
        System.out.println(Arrays.toString(arr));

        int dataCnt = 80000000;

        int[] arr1 = new int[dataCnt];

        // 性能测试
        for (int i = 0; i < dataCnt; i++) {
            arr1[i] = (int)(Math.random() * dataCnt);
        }

        long l1 = System.currentTimeMillis();
        // BubbleSort.sort(arr1);
        // SelectSort.sort(arr1);
        // SelectSort.sort(arr1);
        // ShellSort.shiftSort(arr1);
        QuickSort.sort(arr1);
        // MergeSort.sort(arr1);
        long l2 = System.currentTimeMillis();
        System.out.println((l2 - l1));
    }
}
