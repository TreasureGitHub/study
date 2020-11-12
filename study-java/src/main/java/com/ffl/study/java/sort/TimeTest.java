package com.ffl.study.java.sort;

/**
 * @author lff
 * @datetime 2020/06/14 10:54
 */
public class TimeTest {

    public static void main(String[] args) {
        // int[] arr = {2, 5, 1, 3, 8, 5, 7, 4, 3};
        // BubbleSort.sort(arr);
        // SelectSort.sort(arr);
        // System.out.println(Arrays.toString(arr));

        int dataCnt = 80000;

        int[] arr = new int[dataCnt];

        // 性能测试
        for (int i = 0; i < dataCnt; i++) {
            arr[i] = (int)(Math.random() * dataCnt);
        }

        long l1 = System.currentTimeMillis();
        BubbleSort.sort(arr);
        // SelectSort.sort(arr);
        // InsertSort.sort(arr);
        // ShellSort.sort(arr);
        // MergeSort.sort(arr);
        // QuickSort.sort(arr);
        // HeapSort.sort(arr);
        // RadixSort.sort(arr);
        long l2 = System.currentTimeMillis();
        System.out.println((l2 - l1));
    }
}
