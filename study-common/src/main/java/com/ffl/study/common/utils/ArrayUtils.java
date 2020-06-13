package com.ffl.study.common.utils;

import com.ffl.study.common.constants.CharConstants;

/**
 * @author lff
 * @datetime 2020/06/12 02:05
 */
public class ArrayUtils {

    public static void printTwoDim(int[][] arr){
        printTwoDim(arr, CharConstants.TAB);
    }

    public static void printTwoDim(int[][] arr,String split){
        for (int[] row : arr) {
            for (int data : row) {
                System.out.printf("%d%s", data,split);
            }
            System.out.println();
        }
    }
}
