package com.ffl.study.java.search;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/11/30 08:18
 */
public class FibonacciSearch {

    private static final int maxSize = 20;

    public static void main(String[] args) {
        int[] arr = {1, 8, 10, 89, 1000, 1234};
        System.out.println(search(arr, 111));

    }

    /**
     * 使用非递归方式查找
     *
     * @param arr   数组
     * @param value 要查找的值
     * @return
     */
    public static int search(int[] arr, int value) {
        int low = 0;
        int high = arr.length - 1;
        int k = 0; // 表示斐波拉契分割数值的下标

        // 存放mid的值
        int mid = 0;

        int f[] = fib();
        while (high > f[k] - 1) {
            k++;
        }

        // f(k)可能大于数组的长度，因此需要沟通新的数组并指向 temp
        int[] temp = Arrays.copyOf(arr, f[k]);
        // 实际上需要arr数组最后的数填充 temp
        // 举例
        // {1, 8, 10, 89, 1000, 1234, 0, 0} => {1, 8, 10, 89, 1000, 1234, 1234, 1234}
        for (int i = high + 1; i < temp.length; i++) {
            temp[i] = arr[high];
        }

        while (low <= high) {
            mid = low + f[k - 1] - 1;
            if (value < temp[mid]) {
                high = mid - 1;
                // 1.全部元素 = 前面的元素 + 后面的元素
                // 2.f[k] = f[k - 1] + f[k -2]
                // 因为前面有 f[k-1]个元素，所以我们可以继续拆分 f[k-1] = f[k-2] + f[k-3]
                // 即在f[k-1] 的前面继续查找 k--;即下次循环 mid = f[k-1-1] - 1
                k--;
            } else if (value > temp[mid]) {
                low = mid + 1;
                // 1.全部元素 = 前面的元素 + 后面的元素
                // 2.f[k] = f[k - 1] + f[k -2]
                // 3.因为后面我们有 f[k-2] ，所以可以继续拆分 f[k -1] = f[k-3] + f[k-4]
                // 即在f[k-2] 的前面继续查找 k-= 2 ;即下次循环 mid = f[k-1-2] - 1
                k -= 2;
            } else {
                // 返回小一点的
                return Math.min(mid, high);
            }
        }

        return -1;
    }


    /**
     * 得到斐波拉契 数组
     * 用非递归方式
     *
     * @return
     */
    public static int[] fib() {
        int[] f = new int[maxSize];
        f[0] = 1;
        f[1] = 1;

        for (int i = 2; i < maxSize; i++) {
            f[i] = f[i - 1] + f[i - 2];

        }

        return f;
    }

}
