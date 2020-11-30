package com.ffl.study.java.search;

/**
 * @author lff
 * @datetime 2020/11/30 00:21
 *
 * 插值查找
 */
public class InsertSearch {

    public static void main(String[] args) {
        int[] arr = new int[100];

        // 初始化数组
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }

        // 查找
        search(arr,1);
        BinarySearch.search(arr,1);

    }


    /**
     * @param arr   待查找数组
     * @param value 待查找值
     * @return 如果能查找到则返回下标，否则返回 - 1
     */
    public static int search(int[] arr, int value) {
        if(value < arr[0] || value > arr[arr.length - 1]){
            return -1;
        }

        System.out.println("查找次数~");

        int start = 0;
        int end = arr.length - 1;

        int mid;

        while (start <= end) {
            mid = start + (end - start) *  (value - arr[start]) / (arr[end] - arr[start]);
            if (value == arr[mid]) {
                return mid;
            } else if ( value < arr[mid]) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }

        return -1;
    }

}
