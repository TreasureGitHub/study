package com.ffl.study.java.sort;

import com.ffl.study.common.utils.ArrayUtils;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/06/21 19:30
 * <p>
 * https://www.runoob.com/w3cnote/quick-sort.html
 *
 * https://www.jianshu.com/p/e0364a3166f9
 *
 * https://segmentfault.com/a/1190000021726667?utm_source=tag-newest
 *
 */
public class QuickSort1 {

    public static void main(String[] args) {
        // int[] arr = {1, 5, 7, 4, 9, 2, 6, 8};

        // int[] arr = {1, 5, 7, 4, 4, 9, 2, 6, 4, 8};

        // int[] arr = {6, 1, 2, 6, 6, 6, 3, 8, 6, 6};

        int[] arr = {1,2,3};

        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    public static void quickSort(int[] arr, int left, int right) {

        if(left > right){
            return ;
        }

        int partition = partition(arr, left, right);
        // quickSort(arr, left, partition - 1);
        // quickSort(arr, partition + 1, right);
    }

    /**
     * 双路快排
     *
     * @param arr   数组
     * @param left  左边
     * @param right 右边
     */
    public static int partition(int[] arr, int left, int right) {
        // 取中值数据和 left进行交换
        ArrayUtils.swap(arr, left, (left + right) / 2);

        int low = left + 1;
        int high = right;
        int value = arr[left];

        while (low < high) {

            while (low <= right && arr[low] < value) {
                low++;
            }

            while (high >= left + 1 && arr[high] > value) {
                high--;
            }

            // 遍历完后 low 和 high 会出现三种情况
            // 1.low = high 此时


            if (low >= high) {
                break;
            }

            ArrayUtils.swap(arr, low, high);

            low++;
            high--;
        }

        System.out.println(String.format("low : %d ; high : %d",low, high));

        ArrayUtils.swap(arr, left, high);
        return high;

        // quickSort(arr, left, high - 1);
        // quickSort(arr, high + 1, right);
    }
}
