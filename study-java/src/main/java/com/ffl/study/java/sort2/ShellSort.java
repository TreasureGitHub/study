package com.ffl.study.java.sort2;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/06/19 00:14
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] arr = {2, 5, 1, 3, 8, 5, 7, 4, 3};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        int gap = arr.length;

        while (gap > 0) {
            gap /= 2;

            for (int i = 0; i < arr.length; i += gap) {

            }
        }

    }
}
