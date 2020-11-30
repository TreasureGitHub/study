package com.ffl.study.java.search;

/**
 * @author lff
 * @datetime 2020/11/29 23:51
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = {1, 8, 10, 89, 1000, 1234};
        System.out.println(search(arr, 10));
        System.out.println(search1(arr, 10));
    }

    /**
     * @param arr   待查找数组
     * @param value 待查找值
     * @return 如果能查找到则返回下标，否则返回 - 1
     */
    public static int search(int[] arr, int value) {
        int start = 0;
        int end = arr.length - 1;

        int mid;

        while (start <= end) {
            mid = (start + end) / 2;
            System.out.println("二分查找~~");
            if (value == arr[mid]) {
                return mid;
            } else if (value < arr[mid]) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }

        return -1;
    }

    /**
     * 二分查找，递归实现
     *
     * @param arr   待查找数组
     * @param value 待查找值
     * @return 返回数据
     */
    public static int search1(int[] arr, int value) {
        return search1(arr, 0, arr.length - 1, value);
    }

    /**
     * 二分查找，递归实现
     *
     * @param arr
     * @param start
     * @param end
     * @param value
     * @return
     */
    private static int search1(int[] arr, int start, int end, int value) {
        if (start > end) {
            return -1;
        }

        int mid = (start + end) / 2;
        if (arr[mid] == value) {
            return mid;
        } else if (arr[mid] > value) {
            return search1(arr, start, mid - 1, value); // 向左递归
        } else {
            return search1(arr, mid + 1, end, value); // 向右递归
        }

    }
}
