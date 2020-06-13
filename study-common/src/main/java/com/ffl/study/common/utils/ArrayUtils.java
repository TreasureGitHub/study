package com.ffl.study.common.utils;

import com.ffl.study.common.constants.StringConstants;

/**
 * @author lff
 * @datetime 2020/06/12 02:05
 */
public class ArrayUtils {

    public static void printTwoDim(int[][] arr){
        printTwoDim(arr, StringConstants.TAB);
    }

    public static void printTwoDim(int[][] arr,String join){
        for (int[] row : arr) {
            for (int data : row) {
                System.out.printf("%d%s", data,join);
            }
            System.out.println();
        }
    }
}
