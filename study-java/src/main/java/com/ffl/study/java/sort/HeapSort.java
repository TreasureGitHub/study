package com.ffl.study.java.sort;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/07/05 23:14
 */
public class HeapSort {

    public static void main(String[] args) {
        int[] arr = {6, 4, 8, 2, 5, 7, 9};
        // int[] arr = {6, 4, 8, 2, 5, 7, 9, -1, -2, 0, 10, 20};
        sort(arr);
        // System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        // 形成大顶堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjust(arr, i, arr.length);
        }

        System.out.println(Arrays.toString(arr));

        int temp;
        for (int j = arr.length - 1; j > 0; j--) {

            System.out.println(Arrays.toString(arr));

            // 交换
            temp = arr[j];
            arr[j] = arr[0];
            arr[0] = temp;

            adjust(arr, 0, j);
        }
    }

    /**
     * 将一棵树调整成大顶堆
     *
     * <p>
     * 功能：完成将以 i 对应的非叶子节点的数调整成大顶堆
     * 举例：{4,6,8,5,9} => i = 1    =>   {4,9,8,5,6}
     * 如果我们再次调用adjust 传入的是 i = 0    =>   得到 {4,9,8,5,6} => {9,6,8,5,4}
     *
     * @param arr    待调整的数组
     * @param i      表示非叶子节点在数组中索引
     * @param length 表示对多少个元素调整
     */
    private static void adjust(int[] arr, int i, int length) {
        int temp = arr[i];

        for (int j = i * 2 + 1; j < length; j = j * 2 + 1) {

            // 如果j > j+1 ，说明左节点小于右节点
            if (j + 1 < length && arr[j] < arr[j + 1]) {
                // j 指向右节点
                j++;
            }

            if (arr[j] > temp) {
                // 将较大的值赋值给当前节点
                arr[i] = arr[j];
                // i 指向 j，继续循环比较
                i = j;
            } else {
                break;
            }
        }

        // 当for循环结束后，我们已经将以i 为父节点的树的最大值，放在最顶部(循环)
        // 将temp值放到调整后的位置
        arr[i] = temp;
    }
}
