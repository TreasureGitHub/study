package com.ffl.study.common.utils;

import com.ffl.study.common.constants.StringConstants;

import java.util.StringJoiner;

/**
 * @author lff
 * @datetime 2020/06/12 02:05
 *
 * 数组工具类
 */
public class ArrayUtils {

    /**
     * 打印二维数组
     * 默认值连接符为 \t
     *
     * @param arr 待打印数组
     */
    public static void println(int[][] arr) {
        println(arr, StringConstants.TAB);
    }

    /**
     *
     * @param arr   数组
     * @param join  连接符
     */
    public static void println(int[][] arr, String join) {
        for (int[] row : arr) {
            for (int data : row) {
                System.out.printf("%d%s", data, join);
            }
            System.out.println();
        }
    }

    /**
     * int 型数组交换位置
     *
     * @param arr   数组
     * @param j     数组下标
     * @param i     数组下标
     */
    public static void swap(int[] arr, int i, int j) {
        if (i == j) {
            return;
        }

        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    /**
     * T 型数组 交换位置
     *
     * @param arr   数组
     * @param j     数组下标
     * @param i     数组下标
     * @param <T>   泛型
     */
    public static <T> void swap(T[] arr, int i, int j) {
        T tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * 将传入的int 型数组按照 \t 进行连接
     *
     * @param arr         数组
     * @return            连接后的字符串
     */
    public static String join(int[] arr){
        return join(arr,StringConstants.TAB);
    }

    /**
     * 将字符串型数组转换成int型数组
     *
     * @param arr  字符串型数组
     * @return     int型数组
     */
    public static int[] toIntArray(String[] arr){
        int[] resArr = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            resArr[i] = Integer.parseInt(arr[i]);
        }

        return resArr;
    }

    /**
     * 将传入的int 型数组按照 str 进行连接
     *
     * @param arr         数组
     * @param delimiter   连接符
     * @return            连接后的字符串
     */
    public static String join(int[] arr,String delimiter){
        StringJoiner joiner = new StringJoiner(delimiter);

        for (int i = 0; i < arr.length; i++) {
            joiner.add(String.valueOf(arr[i]));
        }

        return joiner.toString();
    }


    /**
     * 检查 数组中 下标
     *
     * @param len    数组长度
     * @param index  下标
     */
    @Deprecated
    private static void checkIndexRange(int len, int index) {
        if (index > len - 1) {
            throw new IndexOutOfBoundsException(String.format("array size:%d ,index: %d", len, index));
        }
    }

    public static <T> boolean isNullOrEmpty(T[] arr){
        if(arr == null || arr.length == 0){
            return true;
        }

        return false;
    }
}
