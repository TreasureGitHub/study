package com.ffl.study.java.search;

/**
 * @author lff
 * @datetime 2020/06/14 21:32
 */
public class SeqSearch {

    public static void main(String[] args) {
        int arr[] = {1, 9, 11, -1, 34, 89};
        int index = seqSearch(arr, 1);
        System.out.println(index);
    }

    /**
     * 线性查找，找到一个满足条件的
     *
     * @param arr
     * @param value
     * @return
     */
    public static int seqSearch(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return i;
            }
        }

        return -1;
    }
}
