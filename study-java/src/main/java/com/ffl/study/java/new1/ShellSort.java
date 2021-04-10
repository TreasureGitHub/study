package com.ffl.study.java.new1;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/12/26 16:27
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] arr = {9, 3, 8, 5, 1};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        int tmp;
        for (int gap = arr.length / 2; gap >= 1; gap = gap / 2) {
            for (int i = 0; i < arr.length; i++) {
                for (int j = i; j >= 0 && j + gap < arr.length; j = j - gap) {
                    tmp = arr[j + gap];
                    if (arr[j + gap] < arr[j]) {
                        arr[j + gap] = arr[j];
                        arr[j] = tmp;
                    } else {
                        break;
                    }
                }
            }
        }
    }
}
